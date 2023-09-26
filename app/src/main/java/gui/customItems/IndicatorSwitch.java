package gui.customItems;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.util.AttributeSet;
import android.widget.Switch;

public class IndicatorSwitch extends Switch {
    public IndicatorSwitch(Context context) {
        super(context);
    }

    public IndicatorSwitch(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public IndicatorSwitch(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int switchWidth = getWidth();
        int switchHeight = getHeight();

        float textSize =  Math.min(switchWidth, switchHeight)  / 25f;

        // Configurar el tamaño de texto
        setTextSize(textSize);

        super.onDraw(canvas);
    }
}
