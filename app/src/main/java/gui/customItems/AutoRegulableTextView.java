package gui.customItems;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;

public class AutoRegulableTextView extends AppCompatTextView {
    public AutoRegulableTextView(Context context) {
        super(context);
    }

    public AutoRegulableTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public AutoRegulableTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        int textViewWidth = getWidth();
        int textViewHeight = getHeight();

        float textSize = Math.min(textViewWidth, textViewHeight) * 0.15f;

        // Configurar el tamaño de texto
        setTextSize(textSize);


    }







}
