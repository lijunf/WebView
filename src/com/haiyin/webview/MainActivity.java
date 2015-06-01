package com.haiyin.webview;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends Activity {
	
	private WebView webview;  
	private ProgressDialog progressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override 
    //设置回退  
    //覆盖Activity类的onKeyDown(int keyCoder,KeyEvent event)方法  
    public boolean onKeyDown(int keyCode, KeyEvent event) {  
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webview.canGoBack()) {  
            webview.goBack(); //goBack()表示返回WebView的上一页面  
            return true;  
        }  
        return false;  
    }  
      
    //Web视图  
    private class HelloWebViewClient extends WebViewClient {  
        @Override 
        public boolean shouldOverrideUrlLoading(WebView view, String url) {  
            view.loadUrl(url);  
            return true;  
        }  
        
		public HelloWebViewClient() {

		}

		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {// 网页页面开始加载的时候

			if (progressDialog == null) {
				progressDialog = new ProgressDialog(MainActivity.this);
				progressDialog.setMessage("数据加载中，请稍后。。。");
				progressDialog.show();
				webview.setEnabled(false);// 当加载网页的时候将网页进行隐藏
			}
			super.onPageStarted(view, url, favicon);
		}

		@Override
		public void onPageFinished(WebView view, String url) {// 网页加载结束的时候
			if (progressDialog != null && progressDialog.isShowing()) {
				progressDialog.dismiss();
				progressDialog = null;
				webview.setEnabled(true);
			}
		}
    }  

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container, false);
			webview = (WebView) rootView.findViewById(R.id.webview);
			WebSettings webSetting = webview.getSettings();
			webSetting.setJavaScriptEnabled(true);
			webSetting.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
			webview.loadUrl("http://www.51cto.com/");
			webview.setWebViewClient(new HelloWebViewClient());
			return rootView;
		}
	}
}
