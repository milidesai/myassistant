package myapp.myorganizer;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

/**
 * Created by Mili on 27-06-2016.
 */
public class about extends Fragment {
    private WebView webView;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.about, container,
                false);
        webView  = (WebView) rootView.findViewById(R.id.webview_compontent);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("file:///android_asset/about.html");
        return rootView;
    }

    }
