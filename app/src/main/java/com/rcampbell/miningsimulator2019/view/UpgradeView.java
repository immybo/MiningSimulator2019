package com.rcampbell.miningsimulator2019.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.rcampbell.miningsimulator2019.model.Universe;
import com.rcampbell.miningsimulator2019.model.upgrade.Upgrade;

import java.util.HashMap;
import java.util.Map;

public class UpgradeView extends LinearLayout {
    private Upgrade[] upgrades;
    private Map<String, ImageButton> imagesByUpgradeName;

    public UpgradeView(Context context) {
        super(context);
    }

    public UpgradeView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public UpgradeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (upgrades == null || imagesByUpgradeName == null) return;

        Paint paint = new Paint();
        paint.setStrokeWidth(3);
        paint.setColor(Color.BLACK);

        for (Upgrade upgrade : upgrades) {
            ImageButton upgradeButton = imagesByUpgradeName.get(upgrade.getName());
            for (Upgrade dependency : upgrade.getDependencies()) {
                ImageButton dependencyButton = imagesByUpgradeName.get(dependency.getName());
                canvas.drawLine(upgradeButton.getX()+upgradeButton.getWidth()/2, upgradeButton.getY()+upgradeButton.getHeight()/2, dependencyButton.getX()+dependencyButton.getWidth()/2, dependencyButton.getY()+dependencyButton.getHeight()/2, paint);
            }
        }
    }

    public void initialise(Universe universe, ViewUpdateListener listener) {
        imagesByUpgradeName = new HashMap<String, ImageButton>();

        Upgrade[] upgrades = Upgrade.getAll();

        for (Upgrade upgrade : upgrades) {
            ImageButton button = getUpgradeButton(upgrade, universe, listener);
            this.addView(button);
            imagesByUpgradeName.put(upgrade.getName(), button   );
        }

        this.upgrades = upgrades;
    }

    private ImageButton getUpgradeButton(final Upgrade upgrade, final Universe universe, final ViewUpdateListener listener) {
        final ImageButton button = new ImageButton(getContext());
        button.setImageResource(upgrade.getIcon());
        button.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (universe.robotIsAboveGround() && universe.getPlayerMoney() >= upgrade.getCost() && upgrade.hasAllDependencies(universe.getRobot())) {
                    universe.spendMoney(upgrade.getCost());
                    upgrade.apply(universe.getRobot());
                    button.setImageAlpha(127);
                    listener.refresh();
                }
            }
        });
        return button;
    }
}
