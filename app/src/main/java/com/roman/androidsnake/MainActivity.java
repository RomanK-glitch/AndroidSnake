package com.roman.androidsnake;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import androidx.annotation.Nullable;

public class MainActivity extends Activity {
    Snake snake;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MySurfaceView surfaceView = new MySurfaceView((this));
        surfaceView.setOnTouchListener(new OnSwipeTouchListener(this) {
            public void onSwipeTop() {
                if ((snake != null) && (snake.direction != Direction.DOWN)){
                    snake.direction = Direction.UP;
                }
            }
            public void onSwipeRight() {
                if ((snake != null) && (snake.direction != Direction.LEFT)){
                    snake.direction = Direction.RIGHT;
                }
            }
            public void onSwipeLeft() {
                if ((snake != null) && (snake.direction != Direction.RIGHT)){
                    snake.direction = Direction.LEFT;
                }
            }
            public void onSwipeBottom() {
                if ((snake != null) && (snake.direction != Direction.UP)){
                    snake.direction = Direction.DOWN;
                }
            }
        });
        setContentView(surfaceView);
    }

public class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback {
    private DrawThread drawThread;

    public MySurfaceView(Context context) {
        super(context);
        getHolder().addCallback(this);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        drawThread = new DrawThread(getHolder(), getResources());
        drawThread.setRunning(true);
        drawThread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        // завершаем работу потока
        drawThread.setRunning(false);
        while (retry) {
            try {
                drawThread.join();
                retry = false;
            } catch (InterruptedException e) {
                // если не получилось, то будем пытаться еще и еще
            }
        }
    }
}

    class DrawThread extends Thread{
        private boolean runFlag = false;
        private SurfaceHolder surfaceHolder;
        private long prevTime;

        public DrawThread(SurfaceHolder surfaceHolder, Resources resources){
            this.surfaceHolder = surfaceHolder;

            Point p1 = new Point(100, 100);
            snake = new Snake(p1, 10, Direction.RIGHT);

            // сохраняем текущее время
            prevTime = System.currentTimeMillis();
        }

        public void setRunning(boolean run) {
            runFlag = run;
        }

        @Override
        public void run() {
            Canvas canvas;
            Display display = getWindowManager().getDefaultDisplay();
            int width = display.getWidth();
            int height = display.getHeight();
            FoodCreator foodCreator = new FoodCreator(width, height);
            Paint paintGreen = new Paint();
            paintGreen.setColor(Color.GREEN);
            Point food = foodCreator.CreateFood();
            Walls walls = new Walls(width, height);
            while (runFlag) {
                // получаем текущее время и вычисляем разницу с предыдущим
                // сохраненным моментом времени
                long now = System.currentTimeMillis();
                long elapsedTime = now - prevTime;
                if (elapsedTime > 100){
                    // если прошло больше 300 миллисекунд - сохраним текущее время
                    // изменим координаты
                    prevTime = now;
                    //изменить координаты
                    snake.ChangeCoordinates();
                    if (snake.Eat(food)){
                        food = foodCreator.CreateFood();
                    }
                    if (walls.IsHit(snake) || snake.IsHitTail()){
                        System.exit(0);
                    }
                }
                canvas = null;
                try {
                    // получаем объект Canvas и выполняем отрисовку
                    canvas = surfaceHolder.lockCanvas(null);
                    synchronized (surfaceHolder) {
                        //walls.Draw(canvas, paintGreen);
                        draw(canvas, snake, walls);
                        food.Draw(canvas, paintGreen);
                    }
                }
                finally {
                    if (canvas != null) {
                        // отрисовка выполнена. выводим результат на экран
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }
                }
            }
        }

        public void draw(Canvas canvas, Snake snake, Walls walls){
            Paint paintBlack = new Paint();
            Paint paintWhite = new Paint();
            Paint paintPurple = new Paint();
            Display display = getWindowManager().getDefaultDisplay();
            paintBlack.setColor(Color.BLACK);
            paintWhite.setColor(Color.WHITE);
            paintPurple.setColor(Color.parseColor("#FF33FF"));
            canvas.drawColor(Color.WHITE);

            walls.Draw(canvas, paintPurple);

            Point p1 = new Point(100, 100);

            snake.Draw(canvas, paintBlack);

            Point head = snake.GetHead();
            Point tail = snake.GetTail();
            snake.Move(canvas, paintBlack, paintWhite, tail, head);
        }
    }
}


