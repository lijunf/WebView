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
    //���û���  
    //����Activity���onKeyDown(int keyCoder,KeyEvent event)����  
    public boolean onKeyDown(int keyCode, KeyEvent event) {  
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webview.canGoBack()) {  
            webview.goBack(); //goBack()��ʾ����WebView����һҳ��  
            return true;  
        }  
        return false;  
    }  
      
    //Web��ͼ  
    private class HelloWebViewClient extends WebViewClient {  
        @Override 
        public boolean shouldOverrideUrlLoading(WebView view, String url) {  
            view.loadUrl(url);  
            return true;  
        }  
        
		public HelloWebViewClient() {

		}

		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {// ��ҳҳ�濪ʼ���ص�ʱ��

			if (progressDialog == null) {
				progressDialog = new ProgressDialog(MainActivity.this);
				progressDialog.setMessage("���ݼ����У����Ժ󡣡���");
				progressDialog.show();
				webview.setEnabled(false);// ��������ҳ��ʱ����ҳ��������
			}
			super.onPageStarted(view, url, favicon);
		}

		@Override
		public void onPageFinished(WebView view, String url) {// ��ҳ���ؽ�����ʱ��
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
