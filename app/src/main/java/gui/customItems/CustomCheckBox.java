package gui.customItems;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.CheckBox;

public class CustomCheckBox extends androidx.appcompat.widget.AppCompatCheckBox {
    public CustomCheckBox(Context context) {
        super(context);
    }

    public CustomCheckBox(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomCheckBox(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        int checkboxWidth = getWidth();
        int checkboxHeight = getHeight();

        float textSize =  Math.min(checkboxWidth, checkboxHeight)  / 10f;

        // Configurar el tamaño de texto
        setTextSize(textSize);

        super.onDraw(canvas);
    }
}
