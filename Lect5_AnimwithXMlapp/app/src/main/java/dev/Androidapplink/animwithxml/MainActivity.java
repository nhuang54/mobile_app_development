//Ref: http://androidapplink.blogspot.com/2015/06/animation-with-xml-at-lunching-activity.html

package dev.Androidapplink.animwithxml;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends Activity {

// Button Object References
 	Button blink, bounce, fadein, fadeout, flip, move, rotate, sequential,
			slidedown, slideup, together, wavscale, zoomin, zoomout;

	ImageButton btnMonkey;

//Animation Object References (xml animation files, located in: .\res\anim\..)
	Animation blinkanim, bounceanim, fadeinanim, fadeoutanim, flipanim,
			moveanim, rotateanim, sequeanim, slidedownanim, slideupanim,
			togetheranim, wavscaleanim, zoominanim, zoomoutanim;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		btnMonkey = (ImageButton) findViewById(R.id.btnMonkey);

		btnMonkey.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				btnMonkey.animate().setDuration(5000);
				btnMonkey.animate().alpha(0.25f);
				btnMonkey.animate().rotation(180);
//				btnMonkey.clearAnimation();  //FIX ME!  What happens if you don't call this after the animation completes? A: _____________
			}
		});

// binding animation xml to an Animation Object.
		blinkanim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blink);
		bounceanim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);
		fadeinanim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
		fadeoutanim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_out);
		flipanim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.flip);
		moveanim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.move);
		rotateanim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate);
		sequeanim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.sequential);
		slidedownanim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down);
		slideupanim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up);
		togetheranim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.together);
		wavscaleanim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.wav_scale2);
		zoominanim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom_in);
		zoomoutanim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom_out);

		blink = (Button) findViewById(R.id.Blink);
		blink.startAnimation(blinkanim);  // "Uncomment if you want to start automatic anim at lunching app/activity"

		bounce = (Button) findViewById(R.id.Bounce);
		bounce.startAnimation(bounceanim); // "Uncomment if you want to start automatic anim at lunching app/activity"

		fadein = (Button) findViewById(R.id.Fadein);
		fadein.startAnimation(fadeinanim); // "Uncomment if you want to start automatic anim at lunching app/activity"

		fadeout = (Button) findViewById(R.id.Fadeout);
		fadeout.startAnimation(fadeoutanim); // "Uncomment if you want to start automatic anim at lunching app/activity"

		flip = (Button) findViewById(R.id.Flip);
		flip.startAnimation(flipanim); // "Uncomment if you want to start automatic anim at lunching app/activity"

		move = (Button) findViewById(R.id.Move);
		move.startAnimation(moveanim); // "Uncomment if you want to start automatic anim at lunching app/activity"

		rotate = (Button) findViewById(R.id.Rotate);
		rotate.startAnimation(rotateanim); // "Uncomment if you want to start automatic anim at lunching app/activity"

		sequential = (Button) findViewById(R.id.Sequential);
		sequential.startAnimation(sequeanim); // "Uncomment if you want to start automatic anim at lunching app/activity"

		slidedown = (Button) findViewById(R.id.Slidedown);
		slidedown.startAnimation(slidedownanim); // "Uncomment if you want to start automatic anim at lunching app/activity"

		slideup = (Button) findViewById(R.id.Slideup);
		slideup.startAnimation(slideupanim); // "Uncomment if you want to start automatic anim at lunching app/activity"

		together = (Button) findViewById(R.id.Together);
		together.startAnimation(togetheranim); // "Uncomment if you want to start automatic anim at lunching app/activity"

		wavscale = (Button) findViewById(R.id.Wavscale);
		wavscale.startAnimation(wavscaleanim); // "Uncomment if you want to start automatic anim at lunching app/activity"

		zoomin = (Button) findViewById(R.id.Zoomin);
		zoomin.startAnimation(zoominanim); // "Uncomment if you want to start automatic anim at lunching app/activity"

		zoomout = (Button) findViewById(R.id.Zoomout);
		zoomout.startAnimation(zoomoutanim); // "Uncomment if you want to start automatic anim at lunching app/activity"

		blink.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				//btnMonkey.clearAnimation();  //misc, just testing, ignore...
				//start anim on click button
				blink.startAnimation(blinkanim);
				Toast.makeText(getApplicationContext(), "blink animation",
						Toast.LENGTH_SHORT).show();
			}
		});
		
		bounce.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				//start anim on click button
				bounce.startAnimation(bounceanim);
				Toast.makeText(getApplicationContext(), "bounce animation",
						Toast.LENGTH_SHORT).show();
			}
		});
		
		fadein.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				//start anim on click button
				fadein.startAnimation(fadeinanim);
				Toast.makeText(getApplicationContext(), "fadein animation",
						Toast.LENGTH_SHORT).show();
			}
		});
		
		fadeout.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				//start anim on click button
				fadeout.startAnimation(fadeoutanim);
				Toast.makeText(getApplicationContext(), "fadeout animation",
						Toast.LENGTH_SHORT).show();
			}
		});
		
		flip.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				//start anim on click button
				flip.startAnimation(flipanim);
				Toast.makeText(getApplicationContext(), "flip animation",
						Toast.LENGTH_SHORT).show();
			}
		});
		
		move.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				//start anim on click button
				move.startAnimation(moveanim);
				Toast.makeText(getApplicationContext(), "move animation",
						Toast.LENGTH_SHORT).show();
			}
		});
		
		rotate.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				//start anim on click button
				rotate.startAnimation(rotateanim);
				Toast.makeText(getApplicationContext(), "rotate animation",
						Toast.LENGTH_SHORT).show();
			}
		});
		
		sequential.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				//start anim on click button
				sequential.startAnimation(sequeanim);
				Toast.makeText(getApplicationContext(), "sequential animation",
						Toast.LENGTH_SHORT).show();
			}
		});
		
		slidedown.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				//start anim on click button
				slidedown.startAnimation(slidedownanim);
				Toast.makeText(getApplicationContext(), "slidedown animation",
						Toast.LENGTH_SHORT).show();
			}
		});
		
		slideup.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				//start anim on click button
				slideup.startAnimation(slideupanim);
				Toast.makeText(getApplicationContext(), "slideup animation",
						Toast.LENGTH_SHORT).show();
			}
		});
		
		together.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				//start anim on click button
				together.startAnimation(togetheranim);
				Toast.makeText(getApplicationContext(), "together animation",
						Toast.LENGTH_SHORT).show();
			}
		});
		
		wavscale.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				//start anim on click button
				wavscale.startAnimation(wavscaleanim);
				Toast.makeText(getApplicationContext(), "wav scale animation",
						Toast.LENGTH_SHORT).show();
			}
		});
		
		zoomin.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				//start anim on click button
				zoomin.startAnimation(zoominanim);
				Toast.makeText(getApplicationContext(), "zoomin animation",
						Toast.LENGTH_SHORT).show();
			}
		});
		
		zoomout.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				//start anim on click button
				zoomout.startAnimation(zoomoutanim);
				Toast.makeText(getApplicationContext(), "zoomout animation",
						Toast.LENGTH_SHORT).show();
			}
		});

	}

}
