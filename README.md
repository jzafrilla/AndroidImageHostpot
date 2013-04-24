AndroidImageHostpot
===================

REFS : https://github.com/chrisbanes/PhotoView

Example how to add clickable Hotspots on ImageViews with zoom and pan suppots.

This is an example:

`setContentView(R.layout.activity_main);
RelativeLayout relative = (RelativeLayout) findViewById(R.id.relative);
imageHotSpot = new ImageView(getApplicationContext());  		
imageHotSpot .setImageResource(R.drawable.ic_launcher);
relative.addView(imageHotSpot , new LayoutParams(100, 100));
imageHotSpot .setOnClickListener(new OnClickListener() {
	
	@Override
	public void onClick(View v) {
		//Do something when user tap this image hotspot
	}
});
//This is the root image,
mImageView = (ImageView) findViewById(R.id.iv_photo);
Drawable bitmap = getResources().getDrawable(R.drawable.wallpaper);
mImageView.setImageDrawable(bitmap);
// The MAGIC happens here!
mAttacher = new PhotoViewAttacher(mImageView,imageHotSpot);`

Inside PhotoViewAttacher (You can find original source code in https://github.com/chrisbanes/PhotoView)
on method (setImageViewMatrix) you can calculate the hotspot position. Adapt this to your needs (By passing an arrayList of hotspots, for example): 

`	private void setImageViewMatrix(Matrix matrix) {
		ImageView imageView = getImageView();
		if (null != imageView) {

			checkImageViewScaleType();
			imageView.setImageMatrix(matrix);

			// Call MatrixChangedListener if needed
			if (null != mMatrixChangeListener) {
				RectF displayRect = getDisplayRect(matrix);
				RelativeLayout.LayoutParams params = (LayoutParams) imageHotSpot.getLayoutParams();
				int x = 512; //hardcoded x position of this hotspot
				int y = 320; //hardcoded y position of this hotspot
				
				int finalX = (int) (displayRect.left+((x*(displayRect.right-displayRect.left)/1024)));
				int finalY = (int) (displayRect.top+((y*(displayRect.bottom-displayRect.top)/640)));
				params.leftMargin=(int) finalX-50;
				params.topMargin=(int) finalY-50;
				imageHotSpot.setLayoutParams(params);
				
				
				if (null != displayRect) {
					mMatrixChangeListener.onMatrixChanged(displayRect);
				}
			}
		}
	}`
