package com.mosesmin.android.crimeintentmm;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.FileProvider;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static android.widget.CompoundButton.*;

/**
 *
 * @Misson&Goal 代码以交朋友、传福音
 * @ClassName CrimeFragment
 * @Description TODO
 * @Author MosesMin
 * @Date 2023-08-28 0:40
 * @Version 1.0
 *
 */
public class CrimeFragment extends Fragment {

    private static final String ARG_CRIME_ID = "crime_id";
    private static final String TAG = "CrimeFragment";
    private static final String DIALOG_DATE = "DialogDate";

    private static final int REQUEST_DATE = 0;
    // 15.4.3 获取联系人信息  15-11 添加嫌疑人按钮成员变量
    private static final int REQUEST_CONTACT = 1;
    // 16.3 使用相机intent 16-8 使用相机intent
    private static final int REQUEST_PHOTO= 2;

    private Crime mCrime;
    // 16.3 使用相机intent 16-7 获取照片文件位置
    private File mPhotoFile;

    private EditText mTitleField;
    private Button mDateButton;
    private CheckBox mSolvedCheckbox;

    // 15.4.3 获取联系人信息  15-11 添加嫌疑人按钮成员变量
    private Button mSuspectButton;

    // 15.4.2 发送消息  15-9 发送消息
    private Button mReportButton;

    // 16.1 布置照片 16-1 添加实例变量
    private ImageButton mPhotoButton;
    private ImageView mPhotoView;

    // 17-11 新增CrimeFragment回调接口
    private Callbacks mCallbacks;

    // 17-11 新增CrimeFragment回调接口
    /**
     * Required interface for hosting activities
     */
    public interface Callbacks {
        void onCrimeUpdated(Crime crime);
    }

    public static CrimeFragment newInstance(UUID crimeId){
        Bundle args = new Bundle();
        args.putSerializable(ARG_CRIME_ID,crimeId);

        CrimeFragment fragment = new CrimeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    // 17-11 新增CrimeFragment回调接口
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallbacks = (Callbacks) context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID crimeId = (UUID) getArguments().getSerializable(ARG_CRIME_ID);
        Log.i(TAG, "onCreate: Hosting Activity is : " + getActivity().getClass().getName());
        mCrime = CrimeLab.getInstance(getActivity()).getCrime(crimeId);
        // 16.3 使用相机intent 16-7 获取照片文件位置
        mPhotoFile = CrimeLab.getInstance(getActivity()).getPhotoFile(mCrime);
    }

    // 14.4.2 插入和更新记录 14-11  Crime 数据刷新
    @Override
    public void onPause() {
        super.onPause();

        CrimeLab.getInstance(getActivity())
                .updateCrime(mCrime);
    }

    // 17-11 新增CrimeFragment回调接口
    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_crime, container, false);

        mTitleField = (EditText) v.findViewById(R.id.crime_title);
        mTitleField.setText(mCrime.getTitle());
        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mCrime.setTitle(s.toString());
                //17-15 调用onCrimeUpdated(Crime)方法
                updateCrime();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mDateButton = (Button) v.findViewById(R.id.crime_date);
        updateDate();
        mDateButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getFragmentManager();
                DatePiakcerFragment dialog = DatePiakcerFragment.newInstance(mCrime.getDate());
                dialog.setTargetFragment(CrimeFragment.this,REQUEST_DATE);
                dialog.show(manager,DIALOG_DATE);
            }
        });

        mSolvedCheckbox = (CheckBox) v.findViewById(R.id.crime_solved);
        mSolvedCheckbox.setChecked(mCrime.isSolved());
        mSolvedCheckbox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                mCrime.setSolved(isChecked);
                //17-15 调用onCrimeUpdated(Crime)方法
                updateCrime();
            }
        });

        // 15.4.2 发送消息  15-9 发送消息
        mReportButton = (Button) v.findViewById(R.id.crime_report);
        mReportButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // 接受字符串参数的 Intent 构造方法，我们传入的是一个定义操作的常量。
                // 隐式Intent，不需要指定当前Activity和目标Activity，Android系统自己寻找合适的目标Activity启动
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                // 消息内容
                i.putExtra(Intent.EXTRA_TEXT,getCrimeReport());
                // 消息主题
                i.putExtra(Intent.EXTRA_SUBJECT,
                        getString(R.string.crime_report_subject));
                // 创建一个选择器显示响应隐式intent的全部activity
                i = Intent.createChooser(i,getString(R.string.send_report));
                startActivity(i);
            }
        });

        // 15.4.3 获取联系人信息  15-12 发送隐式intent  让用户从联系人应用里选择嫌疑人 PICK：选择
        /*
        这个隐式intent将由操作
        以及数据获取位置组成。操作为 Intent.ACTION_PICK 。联系人数据获取位置为 ContactsContract.
        Contacts.CONTENT_URI 。简而言之，就是请Android帮忙从联系人数据库里获取某个具体的联系人。
         */
        final Intent pickContact = new Intent(Intent.ACTION_PICK,
                ContactsContract.Contacts.CONTENT_URI);
        //pickContact.addCategory(Intent.CATEGORY_HOME);
        mSuspectButton = (Button) v.findViewById(R.id.crime_suspect);
        mSuspectButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(pickContact,REQUEST_CONTACT);
            }
        });

        if (mCrime.getSuspect() != null){
            mSuspectButton.setText(mCrime.getSuspect());
        }

        // 15.4.4 检查可响应任务的activity  15-14 检查是否存在联系人应用
        // 如果操作系统找不到匹配的activity，应用就会崩溃。
        // 解决办法是首先通过操作系统中的 PackageManager 类进行自检。在onCreateView(...)方法中实现检查
        PackageManager packageManager = getActivity().getPackageManager();
        if (packageManager.resolveActivity(pickContact,
                PackageManager.MATCH_DEFAULT_ONLY) == null){
            mSuspectButton.setEnabled(false);
        }

        // 16.1 布置照片 16-1 添加实例变量
        mPhotoButton = (ImageButton) v.findViewById(R.id.crime_camera);


        // 16.3 使用相机intent 16-8 使用相机intent
        final Intent captureImage = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        boolean canTakePhoto = mPhotoFile != null &&
                captureImage.resolveActivity(packageManager) != null;
        mPhotoButton.setEnabled(canTakePhoto);
        mPhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = FileProvider.getUriForFile(getActivity(),
                        "com.mosesmin.android.crimeintentmm.fileprovider", mPhotoFile);
                captureImage.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                List<ResolveInfo> cameraActivities = getActivity()
                        .getPackageManager().queryIntentActivities(captureImage,
                                PackageManager.MATCH_DEFAULT_ONLY);
                for (ResolveInfo activity : cameraActivities) {
                    getActivity().grantUriPermission(activity.activityInfo.packageName,
                            uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                }
                startActivityForResult(captureImage, REQUEST_PHOTO);
            }
        });

        mPhotoView = (ImageView) v.findViewById(R.id.crime_photo);
        // 16.4 缩放和显示位图 16-12 调用updatePhotoView()方法
        updatePhotoView();

        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK){
            return;
        }
        if (requestCode == REQUEST_DATE){
            Date date = (Date) data.getSerializableExtra(DatePiakcerFragment.EXTRA_DATE);
            mCrime.setDate(date);

            //17-16 再次调用updateCrime()方法
            updateCrime();

            updateDate();
            //else if 15.4.3 获取联系人信息  15-13 获取联系人姓名
        } else if (requestCode == REQUEST_CONTACT && data != null){
            // 获取到Uri，数据定位符
            Uri contactUri = data.getData();
            // Specify which fields you want your query to return values for
            String[] queryFields = new String[]{
                    ContactsContract.Contacts.DISPLAY_NAME
            };

            // Perform your query - the contactUri is like a "where" clause here
            // 通过 ContentResolver 访问 ContentProvider
            Cursor c = getActivity().getContentResolver()
                    .query(contactUri,queryFields,null,null,null);

            try{
                //  Double-check that you actually got results
                if (c.getCount() == 0){
                    return;
                }
                // Pull out the first column of the first row of data that is your suspect's name
                c.moveToFirst();
                String suspect = c.getString(0);
                mCrime.setSuspect(suspect);

                //17-16 再次调用updateCrime()方法
                updateCrime();

                mSuspectButton.setText(suspect);
            }finally {
                c.close();
            }
            // 16.4 缩放和显示位图 16-12 调用updatePhotoView()方法
        }else if (requestCode == REQUEST_PHOTO) {
            Uri uri = FileProvider.getUriForFile(getActivity(),
                    "com.mosesmin.android.crimeintentmm.fileprovider",
                    mPhotoFile);
            getActivity().revokeUriPermission(uri,
                    Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

            //17-16 再次调用updateCrime()方法
            updateCrime();

            updatePhotoView();
        }
    }

    // 17-14 新增updateCrime()方法
    private void updateCrime() {
        CrimeLab.getInstance(getActivity()).updateCrime(mCrime);
        mCallbacks.onCrimeUpdated(mCrime);
    }

    private void updateDate() {
        mDateButton.setText(mCrime.getDate().toString());
    }


    // 15.3 使用格式化字符串 15-8 新增getCrimeReport()方法，创建四段字符串信息，并返回拼接完整的消息
    private String getCrimeReport() {
        String solvedString = null;
        if (mCrime.isSolved()) {
            solvedString = getString(R.string.crime_report_solved);
        } else {
            solvedString = getString(R.string.crime_report_unsolved);
        }

        String dateFormat = "EEE, MMM dd";
        String dateString = DateFormat.format(dateFormat,mCrime.getDate()).toString();

        String suspect = mCrime.getSuspect();
        if (suspect == null){
            suspect = getString(R.string.crime_report_no_suspect);
        }else{
            suspect = getString(R.string.crime_report_suspect,suspect);
        }

        String report = getString(R.string.crime_report,
                mCrime.getTitle(),dateString,solvedString,suspect);

        return report;
    }

    //16.4 缩放和显示位图 16-11 更新mPhotoView
    private void updatePhotoViewBookSource() {
        if (mPhotoFile == null || !mPhotoFile.exists()) {
            mPhotoView.setImageDrawable(null);
        } else {
            Bitmap bitmap = PictureUtils.getScaledBitmap(
                    mPhotoFile.getPath(), getActivity());
            mPhotoView.setImageBitmap(bitmap);
        }
    }

    //16.4 缩放和显示位图 16-11 更新mPhotoView
    private void updatePhotoView() {
        if (mPhotoFile == null || !mPhotoFile.exists()) {
            mPhotoView.setImageDrawable(null);
        } else {
            Bitmap bitmap = PictureUtils.getScaledBitmap(
                    mPhotoFile.getPath(), getActivity());
            mPhotoView.setImageBitmap(bitmap);
        }
    }

    //16.4 缩放和显示位图 16-11 更新mPhotoView
    // 和工作的实际需求有关
    private void updatePhotoViewA() {
        if (mPhotoFile == null || !mPhotoFile.exists()) {
            mPhotoView.setImageDrawable(null);
        } else {
            Bitmap bitmap = PictureUtils.getScaledBitmap(
                    mPhotoFile.getPath(), getActivity());
            Log.i(TAG, "updatePhotoView: bitmap is:" + bitmap);
            Log.i(TAG, "updatePhotoView: bitmap width is:" + bitmap.getWidth() + ",bitmap height is:" + bitmap.getHeight());
            // 参考如下两篇博客 将拍摄的照片统一设置为宽高为128*128
            // https://blog.csdn.net/CHenhong_666/article/details/107109982
            // https://blog.csdn.net/u013597998/article/details/51093728
            int dpi = getDpi();
            int scaleProportion = (int) dpi/160;
            int goalWidth = 128;
            int goalHeight = 128;
            Log.i(TAG, "updatePhotoView: device dpi is:" + dpi);
            Bitmap goalBitmap = PictureUtils.getFixedSizeBitmap(bitmap,scaleProportion*goalWidth,scaleProportion*goalHeight);
            Log.i(TAG, "updatePhotoView: goalBitmap is:" + goalBitmap);
            Log.i(TAG, "updatePhotoView: goalBitmap width is:" + goalBitmap.getWidth() + ",goalBitmap height is:" + goalBitmap.getHeight());
            mPhotoView.setImageBitmap(goalBitmap);
        }
    }

    //16.4 缩放和显示位图 16-11 更新mPhotoView
    // 和工作的实际需求有关 updatePhotoViewB()方法可以替代updatePhotoView()
    private void updatePhotoViewB() {
        if (mPhotoFile == null || !mPhotoFile.exists()) {
            mPhotoView.setImageDrawable(null);
        } else {
            Bitmap bitmap = PictureUtils.getScaledBitmap(
                    mPhotoFile.getPath(), getActivity());
            Log.i(TAG, "updatePhotoView: bitmap is:" + bitmap);
            Log.i(TAG, "updatePhotoView: bitmap width is:" + bitmap.getWidth() + ",bitmap height is:" + bitmap.getHeight());
            // https://blog.csdn.net/CHenhong_666/article/details/107109982
            // https://blog.csdn.net/u013597998/article/details/51093728
            float density = getDensity();
            int goalWidth = 128;
            int goalHeight = 128;
            Log.i(TAG, "updatePhotoView: device density is:" + density);
            Bitmap goalBitmap = PictureUtils.getFixedSizeBitmap(bitmap,(int)density*goalWidth,(int) density*goalHeight);
            Log.i(TAG, "updatePhotoView: goalBitmap is:" + goalBitmap);
            Log.i(TAG, "updatePhotoView: goalBitmap width is:" + goalBitmap.getWidth() + ",goalBitmap height is:" + goalBitmap.getHeight());
            mPhotoView.setImageBitmap(goalBitmap);
        }
    }

    private int getDpi() {
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        int density = dm.densityDpi;
        return density;
    }

    private float getDensity() {
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.density;// dm.density=  densityDpi/160
    }


}