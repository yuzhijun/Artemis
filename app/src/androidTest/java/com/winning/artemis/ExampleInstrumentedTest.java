package com.winning.artemis;

import android.content.Intent;
import android.os.SystemClock;
import android.test.ActivityInstrumentationTestCase2;
import android.view.MotionEvent;

import com.winning.artemis_guard.utils.Sleeper;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleInstrumentedTest extends ActivityInstrumentationTestCase2<MainActivity>{
    public ExampleInstrumentedTest() {
        super(MainActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    public void testMainActivity(){

        Intent intent = new Intent();
        intent.setClassName("com.winning.artemis","com.winning.artemis.MainActivity");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getInstrumentation().startActivitySync(intent);

        MotionEvent event = MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(),
               MotionEvent.ACTION_DOWN, 483.55228f, 1058.4487f, 0);

        MotionEvent event2 = MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(),
                MotionEvent.ACTION_UP, 483.55228f, 1058.4487f, 0);

        getInstrumentation().sendPointerSync(event);
        getInstrumentation().sendPointerSync(event2);

        Sleeper.sleep(2000);

        MotionEvent event3 = MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(),
                MotionEvent.ACTION_DOWN, 141.86864f, 334.8256f, 0);

        MotionEvent event4 = MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(),
                MotionEvent.ACTION_UP, 141.86864f, 334.8256f, 0);

        getInstrumentation().sendPointerSync(event3);
        getInstrumentation().sendPointerSync(event4);


        Sleeper.sleep(2000);

        MotionEvent event5 = MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(),
                MotionEvent.ACTION_DOWN, 503.53375f, 1035.4607f, 0);

        MotionEvent event6 = MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(),
                MotionEvent.ACTION_MOVE, 503.53375f, 1035.4607f, 0);

        MotionEvent event7 = MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(),
                MotionEvent.ACTION_UP, 503.53375f, 1035.4607f, 0);

        getInstrumentation().sendPointerSync(event5);
        getInstrumentation().sendPointerSync(event6);
        getInstrumentation().sendPointerSync(event7);

        Sleeper.sleep(2000);
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }
}
