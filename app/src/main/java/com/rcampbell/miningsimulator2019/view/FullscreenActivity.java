package com.rcampbell.miningsimulator2019.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

public class FullscreenActivity extends AppCompatActivity implements ViewUpdateListener {
    private static final int REFRESH_RATE = 20;

    private MovementController movementController;
    private Universe universe;
    private View hudView;
    private MainView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fullscreen);

        hudView = findViewById(R.id.fullscreen_content_controls);
        gameView = findViewById(R.id.main_view);

        universe = new Universe(this);
        gameView.setUniverse(universe);

        movementController = new MovementController(this, universe.getRobot());

        ((UpgradeView)findViewById(R.id.upgrade_view)).initialise(universe, this);

        addListeners();

        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                redrawGame();
            }
        }, 1, 1000/REFRESH_RATE);
    }

    private void addListeners() {
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

        findViewById(R.id.upgrade_view_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View mainView = findViewById(R.id.main_view);
                View upgradeView = findViewById(R.id.upgrade_view);
                Button upgradeViewButton = (Button)findViewById(R.id.upgrade_view_button);

                if (mainView.getVisibility() == View.VISIBLE) {
                    mainView.setVisibility(View.INVISIBLE);
                    upgradeView.setVisibility(View.VISIBLE);
                    upgradeViewButton.setText(R.string.back_to_main);
                } else {
                    mainView.setVisibility(View.VISIBLE);
                    upgradeView.setVisibility(View.INVISIBLE);
                    upgradeViewButton.setText(R.string.upgrades);
                }
            }
        });
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

        redrawGame();
    }

    public void redrawGame() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                gameView.invalidate();
            }
        });
    }

    private void recalcHud(Universe u) {
        ((TextView)findViewById(R.id.money_label)).setText("$" + u.getPlayerMoney() + ".00");
        ((Button)findViewById(R.id.refuel_button)).setText("Refuel ($" + u.getRobot().getRefuelCost() + ")");
        ((ProgressBar)findViewById(R.id.fuel_guage)).setProgress(u.getRobot().getFuelPercentage());
    }

    @Override
    public void loseGame() {
        universe.getRobot().explode();

        Intent switchToMenu = new Intent(this, MenuActivity.class);
        startActivity(switchToMenu);
    }
}
