package hhn.wearable.activitymonitor;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.util.Log;
import android.view.WindowManager;
import android.widget.TextView;


public class WalkKeeperActivity extends Activity {

    private static final double STRIDE_FACTOR_FEMALE = 0.413;
    private static final double STRIDE_FACTOR_MALE   = 0.415;

    private TextView          mTextView;
    private TextView          stepsCount, caloriesCount;
    private String            gender;
    private int               W, H;
    public final String TAG = "HHN";

    //Sensor related variables
    private SensorManager     mSensorManager;
    private Sensor            mSensor;
    private int               S; // Steps count
    private double            TCB; //Total calories Burnt count

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walk_keeper);
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                mTextView = (TextView) stub.findViewById(R.id.text);
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON); // Flag to keep screen on
                stepsCount = (TextView) findViewById(R.id.text_steps);
                caloriesCount = (TextView) findViewById(R.id.text_calories);
                SharedPreferences preferences = getSharedPreferences(TAG, Context.MODE_PRIVATE);
                gender = preferences.getString("gender", null); // gender is key and female here is default value
                W = preferences.getInt("W", 80);
                H = preferences.getInt("H", 180);

                Log.v(TAG, "Gender : "+ gender +"Weight : "+ W + "Height : "+ H); // Log to see if correct data input has been taken



            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(mSensorEventListener, mSensor, 1000);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(mSensorEventListener);
    }

    private SensorEventListener mSensorEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            S += (int) sensorEvent.values[0];
            TCB = getCalories();
            updateUserInterface();
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }

        private double getCalories(){
            double CBD = W * 2.02;
            double D = 0;
            if (gender.equalsIgnoreCase("female")){
                D = H * STRIDE_FACTOR_FEMALE * 0.00001;

            }else{
                D = H * STRIDE_FACTOR_MALE * 0.00001;
            }
            return D;
        }

        private void updateUserInterface(){
            if(stepsCount != null){
                stepsCount.setText(Integer.toString((S)));
            }
            if(caloriesCount != null){
                caloriesCount.setText(Double.toString(TCB));
            }
        }
    };
}
