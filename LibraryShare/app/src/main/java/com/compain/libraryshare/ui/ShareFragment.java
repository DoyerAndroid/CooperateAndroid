package com.compain.libraryshare.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.Toast;

import com.compain.libraryshare.R;
import com.compain.libraryshare.model.BookBean;
import com.compain.libraryshare.util.AppCache;
import com.compain.libraryshare.util.AppUtil;
import com.compain.libraryshare.util.Bimp;
import com.compain.libraryshare.util.BitmapLoader;
import com.compain.libraryshare.util.ImgFileUtils;
import com.compain.libraryshare.util.ToastUtils;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.INPUT_METHOD_SERVICE;
import static cn.bmob.v3.Bmob.getApplicationContext;
import static com.compain.libraryshare.util.Bimp.drr;

/**
 * Created by doyer.du on 2016/5/15.
 */
public class ShareFragment extends Fragment {
    @Bind(R.id.spinner_inputType)
    Spinner spinner_inputType;
    @Bind(R.id.spinner_inputAge)
    Spinner getSpinner_inputAge;

    public List<Bitmap> bmp = new ArrayList<Bitmap>();
    public static List<Bitmap> bitmaplist = new ArrayList<Bitmap>();
    @Bind(R.id.et_inputName)
    EditText etInputName;
    @Bind(R.id.et_inputAuthor)
    EditText etInputAuthor;
    @Bind(R.id.et_inputIntroduce)
    EditText etInputIntroduce;
    @Bind(R.id.iv_addCover)
    ImageView ivAddCover;
    private String book_cover = "";


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_share, container, false);
        ButterKnife.bind(this, view);
        //类型选择事件
        spinner_inputType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public static ShareFragment newInstance(String content) {
        Bundle args = new Bundle();
        args.putString("ARGS", content);
        ShareFragment fragment = new ShareFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @OnClick({R.id.iv_addCover, R.id.library_upload})
    public void onClick(View v) {
        switch (v.getId()) {
            //添加图片
            case R.id.iv_addCover:
                ((InputMethodManager) this.getActivity().getSystemService(INPUT_METHOD_SERVICE))
                        .hideSoftInputFromWindow(ivAddCover.getWindowToken(),
                                InputMethodManager.HIDE_NOT_ALWAYS);
                String sdcardState = Environment.getExternalStorageState();
                if (Environment.MEDIA_MOUNTED.equals(sdcardState)) {
                    new PopupWindows(this.getContext(), ivAddCover);
                } else {
                    Toast.makeText(getApplicationContext(), "sdcard已拔出，不能选择照片",
                            Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.library_upload:
                String bookName = etInputName.getText().toString();
                String author = etInputAuthor.getText().toString();
                String introduce = etInputIntroduce.getText().toString();
                String type = (String) spinner_inputType.getSelectedItem();
                String age = (String) getSpinner_inputAge.getSelectedItem();
                BookBean bookBean = new BookBean();
                bookBean.setBook_author(author);
                bookBean.setBook_for_age(age);
                bookBean.setBook_introduce(introduce);
                bookBean.setBook_Name(bookName);
                bookBean.setBook_type(type);
                bookBean.setBook_cover(AppCache.QN_Server + book_cover);
                if (TextUtils.isEmpty(bookName) || TextUtils.isEmpty(author) || TextUtils.isEmpty(introduce) || TextUtils.isEmpty(book_cover)) {
                    ToastUtils.show(ShareFragment.this.getContext(), "资料填写不完整哦！");
                    return;
                }
                bookBean.save(new SaveListener<String>() {
                    @Override
                    public void done(String s, BmobException e) {
                        if (e == null) {
                            ToastUtils.show(ShareFragment.this.getContext(), "恭喜您，书本分享成功！");
                        }
                    }
                });
                break;
        }
    }


    public class PopupWindows extends PopupWindow {

        public PopupWindows(Context mContext, View parent) {

            View view = View
                    .inflate(mContext, R.layout.item_popupwindows, null);
            view.startAnimation(AnimationUtils.loadAnimation(mContext,
                    R.anim.fade_ins));
            LinearLayout ll_popup = (LinearLayout) view
                    .findViewById(R.id.ll_popup);
            // ll_popup.startAnimation(AnimationUtils.loadAnimation(mContext,
            // R.anim.push_bottom_in_2));

            setWidth(ViewGroup.LayoutParams.FILL_PARENT);
            setHeight(ViewGroup.LayoutParams.FILL_PARENT);
            setBackgroundDrawable(new BitmapDrawable());
            setFocusable(true);
            setOutsideTouchable(true);
            setContentView(view);
            showAtLocation(parent, Gravity.BOTTOM, 0, 0);
            update();
            Button bt1 = (Button) view
                    .findViewById(R.id.dialog_pick_camera);
            Button bt2 = (Button) view
                    .findViewById(R.id.dialog_pick_picture);
            Button bt3 = (Button) view
                    .findViewById(R.id.dialog_cancel);
            bt1.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    photo();
                    dismiss();
                }
            });
            bt2.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent i = new Intent(
                            // 相册
                            Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(i, RESULT_LOAD_IMAGE);
                    dismiss();
                }
            });
            bt3.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    dismiss();
                }
            });
        }
    }


    private static final int TAKE_PICTURE = 0;
    private static final int RESULT_LOAD_IMAGE = 1;
    private static final int CUT_PHOTO_REQUEST_CODE = 2;
    private static final int SELECTIMG_SEARCH = 3;
    private String path = "";
    private Uri photoUri;

    public void photo() {
        try {
            Intent openCameraIntent = new Intent(
                    MediaStore.ACTION_IMAGE_CAPTURE);

            String sdcardState = Environment.getExternalStorageState();
            String sdcardPathDir = Environment
                    .getExternalStorageDirectory().getPath() + "/tempImage/";
            File file = null;
            if (Environment.MEDIA_MOUNTED.equals(sdcardState)) {
                // 有sd卡，是否有myImage文件夹
                File fileDir = new File(sdcardPathDir);
                if (!fileDir.exists()) {
                    fileDir.mkdirs();
                }
                // 是否有headImg文件
                file = new File(sdcardPathDir + System.currentTimeMillis()
                        + ".JPEG");
            }
            if (file != null) {
                path = file.getPath();
                photoUri = Uri.fromFile(file);
                openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);

                startActivityForResult(openCameraIntent, TAKE_PICTURE);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case TAKE_PICTURE:
                    if (resultCode == -1) {// 拍照
                        startPhotoZoom(photoUri);
                    }
                    break;
                case RESULT_LOAD_IMAGE:
                    if (drr.size() < 6 && resultCode == RESULT_OK && null != data) {// 相册返回
                        Uri uri = data.getData();
                        if (uri != null) {
                            startPhotoZoom(uri);
                        }
                    }
                    break;
                case CUT_PHOTO_REQUEST_CODE:
                    if (resultCode == RESULT_OK && null != data) {// 裁剪返回
                        Bitmap bitmap = Bimp.getLoacalBitmap(drr.get(drr.size() - 1));
                        bitmaplist.add(bitmap);
                        bmp.add(bitmap);
                        final String thumbnail = BitmapLoader.saveBitmapToLocal(getApplicationContext(), bitmap);
                        ivAddCover.setImageResource(R.drawable.uploading);
                        AppUtil.upload(thumbnail, new UpCompletionHandler() {
                            @Override
                            public void complete(String key, ResponseInfo info, JSONObject response) {
                                addPicShow(thumbnail);
                                try {
                                    book_cover = (String) response.get("hash");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                ToastUtils.show(ShareFragment.this.getContext(), "图片上传成功");
                            }
                        });

                    }
                    break;
                case SELECTIMG_SEARCH:
                    if (resultCode == RESULT_OK && null != data) {
                        String text = "#" + data.getStringExtra("topic") + "#";
                    }
                    break;
            }
        }
    }

    private void addPicShow(String thumbnail) {
        ivAddCover.setImageURI(Uri.parse(thumbnail));
    }

    private void startPhotoZoom(Uri uri) {
        try {
            // 获取系统时间 然后将裁剪后的图片保存至指定的文件夹
            SimpleDateFormat sDateFormat = new SimpleDateFormat(
                    "yyyyMMddhhmmss");
            String address = sDateFormat.format(new Date());
            if (!ImgFileUtils.isFileExist("")) {
                ImgFileUtils.createSDDir("");
            }
            drr.add(ImgFileUtils.SDPATH + address + ".JPEG");
            Uri imageUri = Uri.parse("file:///sdcard/formats/" + address
                    + ".JPEG");

            final Intent intent = new Intent("com.android.camera.action.CROP");

            // 照片URL地址
            intent.setDataAndType(uri, "image");

            intent.putExtra("crop", "true");
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);
            intent.putExtra("outputX", 400);
            intent.putExtra("outputY", 480);
            // 输出路径
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            // 输出格式
            intent.putExtra("outputFormat",
                    Bitmap.CompressFormat.JPEG.toString());
            // 不启用人脸识别
            intent.putExtra("noFaceDetection", false);
            intent.putExtra("return-data", false);
            startActivityForResult(intent, CUT_PHOTO_REQUEST_CODE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ImgFileUtils.deleteDir(ImgFileUtils.SDPATH);
        ImgFileUtils.deleteDir(ImgFileUtils.SDPATH1);
        // 清理图片缓存
        for (int i = 0; i < bmp.size(); i++) {
            bmp.get(i).recycle();
        }
        for (int i = 0; i < bitmaplist.size(); i++) {
            bitmaplist.get(i).recycle();
        }
        bitmaplist.clear();
        bmp.clear();
        drr.clear();
        ButterKnife.unbind(this);
    }

}
