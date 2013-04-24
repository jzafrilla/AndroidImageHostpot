/*******************************************************************************
 * Copyright 2011, 2012 Chris Banes.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package uk.co.senab.photoview.sample;

import uk.co.senab.photoview.PhotoViewAttacher;
import uk.co.senab.photoview.PhotoViewAttacher.OnMatrixChangedListener;
import uk.co.senab.photoview.PhotoViewAttacher.OnPhotoTapListener;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class SimpleSampleActivity extends Activity {

	static final String PHOTO_TAP_TOAST_STRING = "Photo Tap! X: %.2f %% Y:%.2f %%";

	private ImageView mImageView;
	private TextView mCurrMatrixTv;

	private PhotoViewAttacher mAttacher;

	private Toast mCurrentToast;
	ImageView imagen;
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		RelativeLayout relative = (RelativeLayout) findViewById(R.id.relative);
		imagen = new ImageView(getApplicationContext());
		
		
		imagen.setImageResource(R.drawable.ic_launcher);
		relative.addView(imagen, new LayoutParams(100, 100));
		imagen.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//mGrid.show(imagen);
			}
		});
		mImageView = (ImageView) findViewById(R.id.iv_photo);
		mCurrMatrixTv = (TextView) findViewById(R.id.tv_current_matrix);

		Drawable bitmap = getResources().getDrawable(R.drawable.wallpaper);
		mImageView.setImageDrawable(bitmap);

		// The MAGIC happens here!
		mAttacher = new PhotoViewAttacher(mImageView,imagen);

		// Lets attach some listeners, not required though!
		mAttacher.setOnMatrixChangeListener(new MatrixChangeListener());
		mAttacher.setOnPhotoTapListener(new PhotoTapListener());
	}


	@Override
	public void onDestroy() {
		super.onDestroy();

		// Need to call clean-up
		mAttacher.cleanup();
	}

	

	private class PhotoTapListener implements OnPhotoTapListener {

		@Override
		public void onPhotoTap(View view, float x, float y) {
			float xPercentage = x * 100f;
			float yPercentage = y * 100f;

			if (null != mCurrentToast) {
				mCurrentToast.cancel();
			}

			mCurrentToast = Toast.makeText(SimpleSampleActivity.this,
					String.format(PHOTO_TAP_TOAST_STRING, xPercentage, yPercentage), Toast.LENGTH_SHORT);
			mCurrentToast.show();
		}
	}

	private class MatrixChangeListener implements OnMatrixChangedListener {

		@Override
		public void onMatrixChanged(RectF rect) {
			mCurrMatrixTv.setText(rect.toString());
		}
	}

}
