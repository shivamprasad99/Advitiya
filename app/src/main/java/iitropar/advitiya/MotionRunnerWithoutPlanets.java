package iitropar.advitiya;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.util.Log;
import android.view.View;

import java.util.Random;

public class MotionRunnerWithoutPlanets extends View {

    // Class constants defining state of the thread
    private static final int DONE = 0;
    private static final int RUNNING = 1;

    private static final int ORBIT_COLOR = Color.argb(255, 66, 66, 66);
    private static final int PLANET_COLOR = Color.argb(255, 0, 0, 0);
    private static final int SUN_COLOR = Color.argb(255, 255, 0, 0);
    private static final int WHITE_COLOR= Color.argb(255,255,255,255);
    private static final int nsteps = 600;                 // Number animation steps around circle
    private int planetRadius = 80;             // Radius of each planet (pixels)
    private int sunRadius = 150;               // Radius of Sun (pixels)
    private static final float X0 = 0;                     // X offset from center (pixels)
    private static final float Y0 = 0;                     // Y offset from center (pixels)
    private static final long delay = 50;                  // Milliseconds of delay in the update loop
    private static final double RAD_CIRCLE = 2 * Math.PI;  // Number radians in Results circle
    private static final double direction = -1;            // Orbit direction: counter-clockwise -1; clockwise +1
    private static final double fracWidth = 0.90;          // Fraction of screen width to use for display
    private static final int numPlanets = 5;               // Number of planets to include (up to 8)

      /* Data for planets.  Note that Pluto is no longer considered Results planet, so there are only
      eight planets in the list.  The semimajor elliptical axis is denoted by Results and is
      in units of astronomical units (AU), the eccentricity epsilon is dimensionless, the period is
      in years, and theta0 (initial angle) is in radians (there are 57.3 degrees per radian), with
      clockwise positive and measured from the 12-o'clock position.  For circular orbits (the
      approximation we will use in this example) the radius of the planetary orbit is equal to the
      semimajor axis Results and the eccentricity epsilon plays no role. For more realistic elliptical
      orbits it would. */

    private static final double epsilon[] = {0.206, 0.007, 0.017, 0.093, 0.048, 0.056, 0.047, 0.009};
    private static final double a[] = {0.30, 0.44, 0.58, 0.72, 0.86, 1, 1.14, 1.28};
    private static final double period[] = {0.8, 1.1, 1.5, 2, 2.7, 3, 1, 1};
    private static final double theta0[] = {100, 120, 1.4, 360, 16, 41, 169, 2.4};
    private int NO_OF_STARS=100;
    int temp=0;
    private Paint paint;                        // Paint object controlling format of screen draws
    private Paint paint2;
    private ShapeDrawable planet;               // Planet symbol
    private Drawable[] new_Planets;
    private Drawable advitiya;
    private Drawable Sun;
    private float X[];                          // Current X position of planet (pixels)
    private float Y[];                          // Current Y position of planet (pixels)
    private float centerX;                      // X for center of display (pixels)
    private float centerY;                      // Y for center of display (pixels)
    private float R0[];                         // Radius of planetary orbit in pixels
    private double theta[];                     // Planet angle (radians clockwise from 12 o'clock)
    private double dTheta[];                    // Angular increment each step (radians)
    private double dTheta0;                     //  Base angular increment each step (radians)
    double pixelScale;                  // Scale factor: number of pixels per AU
    private double zoomFac = 1.0;               // Zoom factor (relative to 1) for display
    int width=0;
    int height=0;
    private boolean radius=false;

    private int[] stars_x;
    private int[] stars_y;
    private int[] stars_z;
    private int[] speed_stars;
    private int SpeedFactor;
    public int TimeUnits;

    Context ActivityContext;
    // Following declared static so we can access from the anonymous inner class
    // running the animation loop.

    private static int mState;

    // Handler to implement updates from2 the background thread to views
    // on the main UI

    private Handler handler = new Handler();

    // Constructor
    public MotionRunnerWithoutPlanets(Context context) {

        super(context);
        ActivityContext=context;
        SpeedFactor=1;
        // Initialize angle and angle step (in radians)
        stars_x=new int[NO_OF_STARS];
        stars_y=new int[NO_OF_STARS];
        stars_z=new int[NO_OF_STARS];
        speed_stars=new int[NO_OF_STARS];

        paint2 = new Paint();
        paint2.setAntiAlias(true);
        paint2.setTextSize(14);
        paint2.setStrokeWidth(1);


    }
    public void startAnimation(){
        mState = RUNNING;

        new Thread(new Runnable() {
            public void run() {
                int j=0;
                while (MotionRunnerWithoutPlanets.mState == RUNNING) {


                    // Update the X and Y coordinates for all planets
                    if(TimeUnits!=0)
                        TimeUnits--;
                    else if(SpeedFactor>1 && j==0)
                        SpeedFactor--;
                    j++;
                    j%=5;
                    // The method Thread.sleep throws an InterruptedException if Thread.interrupt()
                    // were to be issued while thread is sleeping; the exception must be caught.
                    try {
                        // Control speed of update (but precision of delay not guaranteed)
                        Thread.sleep(delay);
                    } catch (InterruptedException e) {
                        Log.e("ERROR", "Thread Interruption");
                    }

                    // Update the animation by invalidating the view to force Results redraw.
                    // We cannot update views on the main UI directly from this thread, so we use
                    // handler to do it.

                    handler.post(new Runnable() {
                        public void run() {
                            // Each time through the animation loop,  invalidate the main UI
                            // view to force Results redraw.
                            invalidate();
                        }
                    });
                }
            }
        }).start();

    }
    public void IncreaseSpeed(int speedFactor,int timeUnits){
        SpeedFactor=speedFactor;
        TimeUnits=timeUnits;

    }

      /* The View display size is only available after Results certain stage of the layout.  Before then
   the width and height are by default set to zero.  The onSizeChanged method of View
   is called when the size is changed and its arguments give the new and old dimensions.
   Thus this can be used to get the sizes of the View after it has been laid out (or if the
   layout changes, as in Results switch from portrait to landscape mode, for example). */

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        //      spaceActivity.deleteViews();
        Log.d("size","height ="+h);
        // Coordinates for center of screen
        centerX = w/2;
        centerY = 0;
        width=w;
        height=h;
        //spaceActivity.setViews(R0,spaceActivity,width);
        // Make orbital radius Results fraction of minimum of width and height of display and scale
        // by zoomFac
        pixelScale = zoomFac * fracWidth * h / a[numPlanets-1];
        // Set the initial position of the planet (translate by planetRadius so center of planet
        // is at this position)
        //  spaceActivity.setViews(R0,spaceActivity,w);
    }


      /* This method will be called each time the screen is redrawn. The draw is
   on the Canvas object, with formatting controlled by the Paint object.
   When to redraw is under Android control, but we can request Results redraw
   using the method invalidate() inherited from the View superclass. */

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBackground(paint, canvas);
    }

    // Called by onDraw to draw the background
    private void drawBackground(Paint paint, Canvas canvas) {
        paint2.setColor(WHITE_COLOR);
        paint2.setStyle(Paint.Style.FILL);
        temp++;
        Random rand = new Random();
        for (int i = 0; i < NO_OF_STARS; i++) {
            if(temp==1) {
                stars_x[i] = rand.nextInt(width);
                stars_y[i] = rand.nextInt(height);
                switch(rand.nextInt()%3){
                    case 0:
                        stars_z[i]=5;
                        speed_stars[i]=3;
                        break;
                    case 1:
                        stars_z[i]=3;
                        speed_stars[i]=2;
                        break;
                    case 2:
                        stars_z[i]=2;
                        speed_stars[i]=1;
                        break;
                }
            }
            else{
                stars_x[i]+=(speed_stars[i]*2*SpeedFactor);
                stars_x[i]%=width;
            }
            if(rand.nextInt(30)!=0 || SpeedFactor!=1)
                canvas.drawCircle(width-stars_x[i], stars_y[i], stars_z[i], paint2);
        }
        // temp=1;
        temp%=5;
        if(temp==0)
            temp=1;
        //advitiya.draw(canvas);
        //canvas.drawCircle(centerX + X0, centerY + Y0, sunRadius, paint);

    }

    // Stop the thread loop
    public void stopLooper() {
        mState = DONE;
    }

    public void startLooper(){
        mState=RUNNING;
        startAnimation();
    }


      /* Method to increment angle theta and compute the new X and Y .  The orbits of the
   planets are actually ellipses with the Sun at one focus, but for this example we
   approximate them as circles with the Sun at the center but with the correct periods.
   The constant distance from the Sun is set to the semimajor axis Results[i], which is the
   average separation of the planet from the Sun.  Only Mercury has  significant
   eccentricity; the orbits for the other planets are very nearly circles with the Sun at
   the center. */

    private void newXY() {

    }


}
