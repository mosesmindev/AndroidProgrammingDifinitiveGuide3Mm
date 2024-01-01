package com.mosesmin.android.photogallerymm;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

/**
 * @Version: 1.0
 * @Misson&Goal: 代码以交朋友、传福音
 * @Project:PhotoGalleryMm
 * @Description: TODO  代码清单30-5 创建网页浏览fragment（PhotoPageFragment.java）
 * @Author: MosesMin
 * @Date: 2023-12-31 21:56:58
 */
public class PhotoPageFragment extends VisibleFragment {
    private static final String TAG = "PhotoPageFragment";

    private static final String ARG_URI = "photo_page_url";

    private Uri mUri;
    private WebView mWebView;

    // 代码清单30-10 使用 WebChromeClient （PhotoPageFragment.java） -- 1
    private ProgressBar mProgressBar;

    public static PhotoPageFragment newInstance(Uri uri) {
        Bundle args = new Bundle();
        args.putParcelable(ARG_URI, uri);
        PhotoPageFragment fragment = new PhotoPageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUri = getArguments().getParcelable(ARG_URI);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_photo_page, container, false);

        // 代码清单30-10 使用 WebChromeClient （PhotoPageFragment.java） -- 2start
        mProgressBar = (ProgressBar)v.findViewById(R.id.progress_bar);
        mProgressBar.setMax(100); // WebChromeClient reports in range 0-100
        // 代码清单30-10 使用 WebChromeClient （PhotoPageFragment.java） -- 2end

        mWebView = (WebView) v.findViewById(R.id.web_view);
        // 代码清单30-9 加载URL（PhotoPageFragment.java） -- start
        mWebView.getSettings().setJavaScriptEnabled(true);
        // 代码清单30-10 使用 WebChromeClient （PhotoPageFragment.java） -- 3start
        mWebView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView webView, int newProgress) {
                if (newProgress == 100) {
                    mProgressBar.setVisibility(View.GONE);
                } else {
                    mProgressBar.setVisibility(View.VISIBLE);
                    mProgressBar.setProgress(newProgress);
                }
            }
            public void onReceivedTitle(WebView webView, String title) {
                AppCompatActivity activity = (AppCompatActivity) getActivity();
                activity.getSupportActionBar().setSubtitle(title);
            }
        });
        // 代码清单30-10 使用 WebChromeClient （PhotoPageFragment.java） -- 3end
        mWebView.setWebViewClient(new WebViewClient());
        mWebView.loadUrl(mUri.toString());
        // 代码清单30-9 加载URL（PhotoPageFragment.java） -- end
        return v;
    }
}