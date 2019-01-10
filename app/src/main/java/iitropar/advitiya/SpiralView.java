package iitropar.advitiya;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import java.sql.Time;
import java.util.Calendar;
import java.util.Random;

public class SpiralView extends View {

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
    double[] increment;
    double[] size;
      /* Data for planets.  Note that Pluto is no longer considered Results planet, so there are only
      eight planets in the list.  The semimajor elliptical axis is denoted by Results and is
      in units of astronomical units (AU), the eccentricity epsilon is dimensionless, the period is
      in years, and theta0 (initial angle) is in radians (there are 57.3 degrees per radian), with
      clockwise positive and measured from the 12-o'clock position.  For circular orbits (the
      approximation we will use in this example) the radius of the planetary orbit is equal to the
      semimajor axis Results and the eccentricity epsilon plays no role. For more realistic elliptical
      orbits it would. */

    private static final double epsilon[] = {0.206, 0.007, 0.017, 0.093, 0.048, 0.056, 0.047, 0.009};
    private double a[] ;
    private static final double period[] = {0.8, 1.1, 1.5, 2, 2.7, 3, 1, 1};
    private double theta0[] ;
    private int NO_OF_STARS=250;
    int temp=0;
    private Paint paint;                        // Paint object controlling format of screen draws
    private Paint paint2,paint3,paint4;
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

    Context ActivityContext;
    // Following declared static so we can access from the anonymous inner class
    // running the animation loop.

    private static int mState;

    // Handler to implement updates from2 the background thread to views
    // on the main UI

    private Handler handler = new Handler();
    Context contex;

    // Constructor
    public SpiralView(Context context) {

        super(context);
        contex=context;
        ActivityContext=context;
        dTheta0 = RAD_CIRCLE / ((double) nsteps);     // Angle increment in radians
        theta = new double[NO_OF_STARS];
        dTheta = new double[NO_OF_STARS];
        theta0 = new double[NO_OF_STARS];
        R0 = new float[NO_OF_STARS];
        increment=new double[NO_OF_STARS];
        size=new double[NO_OF_STARS];
        for(int i=0;i<NO_OF_STARS;i++)
            increment[i]=0.1;
        Random rand=new Random();
        a = new double[NO_OF_STARS];
        for (int i = 0; i < NO_OF_STARS; i++) {
            float f;
            theta0[i]=rand.nextInt(120);
            f= (float) (rand.nextInt(26)/13.0+0.5);
            dTheta[i] = direction * dTheta0 /f ;
            theta[i] = -direction * theta0[i];
            R0[i]=rand.nextInt(20);
            size[i]=1;
            if(i==0){

                a[i]=(double) rand.nextInt(2)/4.0+0.2;
            }
            else{
                a[i]=rand.nextInt(1)/3+a[i-1];
            }
        }

        stars_x=new int[NO_OF_STARS];
        stars_y=new int[NO_OF_STARS];
        stars_z=new int[NO_OF_STARS];
        speed_stars=new int[NO_OF_STARS];

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setTextSize(14);
        paint.setStrokeWidth(1);

        // Operation on background thread that updates the main
        // thread through handler.
        startAnimation();

    }
    public void callAgain(int i){
       // dTheta0 = RAD_CIRCLE / ((double) nsteps);     // Angle increment in radians
       // theta = new double[NO_OF_STARS];
       // dTheta = new double[NO_OF_STARS];
       // theta0 = new double[NO_OF_STARS];
      //  R0 = new float[NO_OF_STARS];
//        increment=0.1;
        Random rand=new Random(java.lang.System.currentTimeMillis());
//        Results = new double[NO_OF_STARS];
//        for (int i = 0; i < NO_OF_STARS; i++) {
            float f;
            size[i]=1;
            theta0[i]=rand.nextInt(120);
            f= (float) (rand.nextInt(26)/13.0+0.5);
            dTheta[i] = direction * dTheta0 /f ;
            theta[i] = -direction * theta0[i];
            R0[i]=rand.nextInt(200)+2;
            if(i==0){

                a[i]=(double) rand.nextInt(2)/4.0+0.2;
            }
            else{
                a[i]=rand.nextInt(1)/3+a[i-1];
            }
  //      }

//        stars_x=new int[NO_OF_STARS];
//        stars_y=new int[NO_OF_STARS];
  //      stars_z=new int[NO_OF_STARS];
    //    speed_stars=new int[NO_OF_STARS];

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setTextSize(14);
        paint.setStrokeWidth(1);

    }
    public void startAnimation(){
        mState = RUNNING;

        new Thread(new Runnable() {
            public void run() {
                while (SpiralView.mState == RUNNING) {


                    // Update the X and Y coordinates for all planets
                    newXY();

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
        width=w;
        height=h;
        //spaceActivity.setViews(R0,spaceActivity,width);
        // Make orbital radius Results fraction of minimum of width and height of display and scale
        // by zoomFac
        Random rand=new Random();
        float f= (float) (rand.nextInt(31)/31.0+1);
        pixelScale = zoomFac * fracWidth * h / f;
        centerX=width/2;
        centerY=height/2;
        // Set the initial position of the planet (translate by planetRadius so center of planet
        // is at this position)
        /*for (int i = 0; i < NO_OF_STARS; i++) {
            // Compute R0[] in pixels
            R0[i] = (float) (pixelScale * rand.nextFloat(f));
            stars_x[i] = (int) (centerX - R0[i] * (float) Math.sin(theta[i]) - planetRadius);
            stars_y[i] = (int) (centerY - R0[i] * (float) Math.cos(theta[i]) - planetRadius);
        }*/
//        spaceActivity.setViews(R0,spaceActivity,w);
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
        paint.setColor(WHITE_COLOR);
        paint.setStyle(Paint.Style.FILL);
        temp++;
        Random rand = new Random();
        for(int i=0;i<NO_OF_STARS;i++)
        {
            canvas.drawCircle(stars_x[i], stars_y[i], (int) size[i], paint);
            Log.d("chutzpah","y "+stars_y[i]+" x "+stars_x[i]);
        }
        // temp=1;
        temp%=5;
        if(temp==0)
            temp=1;
        //advitiya.draw(canvas);
        //canvas.drawCircle(centerX + X0, centerY + Y0, sunRadius, paint);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(ORBIT_COLOR);
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
        for(int i=0;i<NO_OF_STARS;i++){
            R0[i]*=1.1;//increment;
            size[i]*=1.03;
//            increment+=0.01;
        }
        int k=0;
        for (int i = 0; i < NO_OF_STARS; i++) {
//            theta[i] += dTheta[i];
            stars_x[i] = (int) ((R0[i] * Math.sin(theta[i])) + centerX - planetRadius);
            stars_y[i] = (int) (centerY - (float) (R0[i] * Math.cos(theta[i])) - planetRadius);
            int j = 0;
            if (stars_x[i] < 0 || stars_x[i] > width || stars_y[i] < 0 || stars_y[i] > height)
                callAgain(i);
            /*while(stars_x[i]>width+2*planetRadius || stars_y[i]<-2*planetRadius || stars_y[i]>height+2*planetRadius || Y[i]<-2*planetRadius)
            {
                theta[i] += dTheta[i];
                X[i] = (float) (R0[i] * Math.sin(theta[i])) + centerX - planetRadius;
                Y[i] = centerY - (float) (R0[i] * Math.cos(theta[i])) - planetRadius;
                j++;
                if(j>1)
                    break;
            }*/


        }
/*        if(k==NO_OF_STARS)
            callAgain();*/
    }

    //method to get R0
    public float[] getR0(int height){
        pixelScale = zoomFac * fracWidth * height / a[numPlanets-1];
        Random rand=new Random();
        for (int i = 0; i < numPlanets; i++) {
            // Compute R0[] in pixels
            R0[i] =(float) (pixelScale * rand.nextInt(5));
        }
        return R0;
    }
}
