package com.rcampbell.miningsimulator2019.view;

import android.annotation.SuppressLint;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.rcampbell.miningsimulator2019.R;
import com.rcampbell.miningsimulator2019.controller.MovementController;
import com.rcampbell.miningsimulator2019.model.MiningRobot;
import com.rcampbell.miningsimulator2019.model.Universe;

import java.util.Timer;
import java.util.TimerTask;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class FullscreenActivity extends AppCompatActivity implements ViewUpdateListener {
    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private static final boolean AUTO_HIDE = true;

    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

    /**
     * Some older devices needs a small delay between UI widget updates
     * and a change of the status and navigation bar.
     */
    private static final int UI_ANIMATION_DELAY = 300;
    private final Handler mHideHandler = new Handler();
    private MainView mContentView;
    private final Runnable mHidePart2Runnable = new Runnable() {
        @SuppressLint("InlinedApi")
        @Override
        public void run() {
            // Delayed removal of status and navigation bar

            // Note that some of these constants are new as of API 16 (Jelly Bean)
            // and API 19 (KitKat). It is safe to use them, as they are inlined
            // at compile-time and do nothing on earlier devices.
            mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    };
    private View mControlsView;
    private final Runnable mShowPart2Runnable = new Runnable() {
        @Override
        public void run() {
            // Delayed display of UI elements
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.show();
            }
        }
    };
    private boolean mVisible;
    private final Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            hide();
        }
    };
    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */
    private final View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (AUTO_HIDE) {
                delayedHide(AUTO_HIDE_DELAY_MILLIS);
            }
            return false;
        }
    };

    private MovementController movementController;
    private Universe universe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fullscreen);

        mVisible = true;
        mControlsView = findViewById(R.id.fullscreen_content_controls);
        mContentView = findViewById(R.id.main_view);

        universe = new Universe();
        mContentView.setUniverse(universe);

        movementController = new MovementController(this, universe.getRobot());

        findViewById(R.id.up_button).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                return handleMove(MiningRobot.Direction.UP, universe, event);
            }
        });

        findViewById(R.id.down_button).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                return handleMove(MiningRobot.Direction.DOWN, universe, event);
            }
        });

        findViewById(R.id.left_button).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                return handleMove(MiningRobot.Direction.LEFT, universe, event);
            }
        });

        findViewById(R.id.right_button).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                return handleMove(MiningRobot.Direction.RIGHT, universe, event);
            }
        });

        findViewById(R.id.upgrade_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (universe.robotIsAboveGround() && universe.getPlayerMoney() >= universe.getRobot().getCostToUpgrade()) {
                    universe.spendMoney(universe.getRobot().getCostToUpgrade());
                    universe.getRobot().upgrade();
                    ((Button)findViewById(R.id.upgrade_button)).setText("Upgrade ($" + universe.getRobot().getCostToUpgrade() + ")");
                    recalcHud(universe);
                }
            }
        });

        findViewById(R.id.refuel_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (universe.robotIsAboveGround()) {
                    int fuelCost = universe.getRobot().refuel(universe.getPlayerMoney());
                    universe.spendMoney(fuelCost);
                    ((Button)findViewById(R.id.refuel_button)).setText("Refuel ($" + universe.getRobot().getRefuelCost() + ")");
                    recalcHud(universe);
                }
            }
        });

        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                mContentView.invalidate();
            }
        }, 1,50);
    }

    private boolean handleMove(final MiningRobot.Direction direction, final Universe universe, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                movementController.handleMovementStart(direction);
                return true;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                movementController.handleMovementStop(direction);
                return true;
            default:
                return false;
        }
    }

    public void refresh() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                recalcHud(universe);
            }
        });
    }

    private void recalcHud(Universe u) {
        ((TextView)findViewById(R.id.money_label)).setText("$" + u.getPlayerMoney() + ".00");
        ((Button)findViewById(R.id.refuel_button)).setText("Refuel ($" + u.getRobot().getRefuelCost() + ")");
        ((ProgressBar)findViewById(R.id.fuel_guage)).setProgress(u.getRobot().getFuelPercentage());
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        delayedHide(100);
    }

    private void toggle() {
        if (mVisible) {
            hide();
        } else {
            show();
        }
    }

    private void hide() {
        // Hide UI first
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        mVisible = false;

        // Schedule a runnable to remove the status and navigation bar after a delay
        mHideHandler.removeCallbacks(mShowPart2Runnable);
        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);
    }

    @SuppressLint("InlinedApi")
    private void show() {
        // Show the system bar
        mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        mVisible = true;

        // Schedule a runnable to display UI elements after a delay
        mHideHandler.removeCallbacks(mHidePart2Runnable);
        mHideHandler.postDelayed(mShowPart2Runnable, UI_ANIMATION_DELAY);
    }

    /**
     * Schedules a call to hide() in delay milliseconds, canceling any
     * previously scheduled calls.
     */
    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }
}
