package paulosalvatore.com.br.codelab_multithread_menus;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.net.URL;

public class MainActivity extends AppCompatActivity {

	public ImageView mImageView;
	public String url_image = "https://img.global.news.samsung.com/br/wp-content/uploads/2017/07/Ocean-Manaus-4.jpg";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mImageView = findViewById(R.id.mImageView);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.mnBusca:
				Toast.makeText(this, "Item Busca Clicado", Toast.LENGTH_SHORT).show();
				break;
			case R.id.mnInfo:
				Toast.makeText(this, "Item Info Clicado", Toast.LENGTH_SHORT).show();
				break;
		}

		return super.onOptionsItemSelected(item);
	}

	public void onClickMultiThread(View v) {
		new Thread(new Runnable() {
			public void run() {
				final Bitmap bitmap = loadImage(url_image);
				mImageView.post(new Runnable() {
					public void run() {
						mImageView.setImageBitmap(bitmap);
					}
				});
			}
		}).start();
	}

	public Bitmap loadImage(String url_image) {
		try {
			URL url = new URL(url_image);
			return BitmapFactory.decodeStream(url.openConnection().getInputStream());
		} catch (Exception e) {
			Log.e("loadImage", e.toString());
		}

		return null;
	}

	public void onClickAssync(View view) {
		new TarefaAssicrona().execute();
	}

	public class TarefaAssicrona extends AsyncTask<Void, Void, Bitmap> {

		private ProgressDialog dialog;

		protected void onPreExecute() {
			dialog = ProgressDialog.show(MainActivity.this, "Aviso", "Carregando imagem");
		}

		protected Bitmap doInBackground(Void... params) {
			return loadImage(url_image);
		}

		protected void onPostExecute(Bitmap result) {
			MainActivity.this.mImageView.setImageBitmap(result);

			dialog.dismiss();
		}
	}
}
