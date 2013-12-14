package com.cscentr.microvisio.draw;

import java.util.ArrayList;
import java.util.ListIterator;

import com.cscentr.microvisio.model.Ellipse;
import com.cscentr.microvisio.model.Figure;
import com.cscentr.microvisio.model.Line;
import com.cscentr.microvisio.model.Model;
import com.cscentr.microvisio.model.Point;
import com.cscentr.microvisio.model.Rectangle;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Vibrator;
import android.util.FloatMath;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

public class Draw extends View {
	public Model model;
	private Track track = new Track();
	private Recognizer recognizer = new Recognizer(1);
	private Canvas canvas;
	private Paint paint = new Paint();
	private static float density;
	private Bitmap image; // ���������� ������
	private int bitMapWidth;
	private int bitMapHeight;
	private int width;
	private int height;
	private int zoomWidth;
	private Figure movingFigure = null;
	String text = "";
	private int mode = 1;
	private ColorPickerDialog colorDialog;
	private Point previous, next;
	private float zoom; // ���������� �� ������
	private Point position = new Point(0, 0); // ������� ������
	private boolean drawingMode = false;
	// private Vibrator vibrator;
	// private Handler handler = new Handler();
	private long lastTapTime;
	private Point lastTapPosition;
	private Point cursor = new Point(0, 0);
	private ArrayList<Finger> fingers = new ArrayList<Finger>(); // ��� ������,
																	// �����������
																	// �� ������
	private double maxArrowSize = 30;

	public Canvas getCanvas() {
		return canvas;
	}

	public void setCanvas(Canvas canvas) {
		this.canvas = canvas;
	}

	public boolean getDrawingMode() {
		return drawingMode;
	}

	public void setgetDrawingMode(boolean drawing) {
		drawingMode = drawing;
	}

	public Paint getPaint() {
		return paint;
	}

	public void setPaint(Paint paint) {
		this.paint = paint;
	}

	public Figure getMovingFigure() {
		return movingFigure;
	}

	public void setMovingFigure(Figure figure) {
		this.movingFigure = figure;
	}

	public Track getTrack() {
		return track;
	}

	public void setTrack(Track track) {
		this.track = track;
	}

	public Recognizer getRecognizer() {
		return recognizer;
	}

	public void setRecognizer(Recognizer recognizer) {
		this.recognizer = recognizer;
	}

	public Bitmap getImage() {
		return image;
	}

	public void setImage(Bitmap image) {
		this.image = image;
	}

	public double getZoom() {
		return zoom;
	}

	public void setZoom(float zoom) {
		this.zoom = zoom;
	}

	public Point getPosition() {
		return position;
	}

	public void setPosition(Point position) {
		this.position = position;
	}

	public int getBitMapWidth() {
		return bitMapWidth;
	}

	public void setBitMapWidth(int bitMapWidth) {
		this.bitMapWidth = bitMapWidth;
	}

	public int getBitMapHeight() {
		return bitMapHeight;
	}

	public void setBitMapHeight(int bitMapHeight) {
		this.bitMapHeight = bitMapHeight;
	}

	public Draw(Context context, Model model1, int width1, int height1) {
		super(context);
		model = model1;
		setDensity(getResources().getDisplayMetrics().density);
		bitMapWidth = Math.max(width1 * 2, height1);
		width = width1;
		height = height1;
		zoomWidth = Math.max(width1, height1);
		zoom = zoomWidth;
		this.setBackgroundColor(Color.GRAY); // ������������� ����� ���� ����
		paint.setStrokeWidth(2 * getDensity()); // ������������� ������ ����� �
												// 2dp
		image = Bitmap.createBitmap(bitMapWidth, bitMapWidth, Config.ARGB_4444);
		// ������ ���������� ������
		canvas = new Canvas(image); // ������ �����
		canvas.drawColor(Color.WHITE); // ����������� ��� ����� ������
		paint.setColor(Color.BLACK);
		// vibrator =
		// (Vibrator)context.getSystemService(Context.VIBRATOR_SERVICE);
		/*
		 * new ColorPickerDialog(context, new
		 * ColorPickerDialog.OnColorChangedListener() { public void
		 * colorChanged(int color) { //you change your color when the color is
		 * changed in the dialog paint.setColor(color); } },
		 * paint.getColor()).show();
		 */
	}

	private int minTextSize = 2;

	void drawObject(Canvas canvas, Ellipse ellipse) {
		Paint mypaint = new Paint();
		mypaint.setStyle(Paint.Style.FILL);
		mypaint.setColor(ellipse.getColor());
		mypaint.setStrokeWidth(ellipse.getSize() * getDensity());
		mypaint.setStyle(Style.STROKE);
		if (!ellipse.getText().equals("")) {
			setTextWidth(mypaint, ellipse);
			if (ellipse.getText().length() > 1)
				canvas.drawText(ellipse.getText(), (int) (ellipse.getPoint()
						.getX() - ellipse.getRx() * 0.8), ellipse.getPoint()
						.getY(), mypaint);
			else
				canvas.drawText(ellipse.getText(), (int) (ellipse.getPoint()
						.getX() - ellipse.getRx() * 0.8), (int) (ellipse
						.getPoint().getY() + ellipse.getRy() * 0.9), mypaint);
		}
		RectF rect = new RectF(ellipse.getPoint().getX() - ellipse.getRx(),
				ellipse.getPoint().getY() - ellipse.getRy(), ellipse.getPoint()
						.getX() + ellipse.getRx(), ellipse.getPoint().getY()
						+ ellipse.getRy());
		canvas.drawOval(rect, mypaint);
	}

	void drawObject(Canvas canvas, Rectangle rectangle) {
		Paint mypaint = new Paint();
		mypaint.setStyle(Paint.Style.FILL);
		mypaint.setColor(rectangle.getColor());
		mypaint.setStrokeWidth(rectangle.getSize() * getDensity());
		mypaint.setStyle(Style.STROKE);
		if (!rectangle.getText().equals("")) {
			setTextWidth(mypaint, rectangle);
			if (rectangle.getText().length() > 1)
				canvas.drawText(rectangle.getText(), (int) (rectangle
						.getPoint().getX() - rectangle.getRx() * 0.8),
						(int) (rectangle.getPoint().getY()), mypaint);
			else
				canvas.drawText(
						rectangle.getText(),
						(int) (rectangle.getPoint().getX() - rectangle.getRx() * 0.8),
						(int) (rectangle.getPoint().getY() + rectangle.getRy() * 0.9),
						mypaint);
		}
		rectangle.calculatePoint();
		canvas.drawRect(rectangle.getLeftTop().getX(), rectangle.getLeftTop()
				.getY(), rectangle.getRightBottom().getX(), rectangle
				.getRightBottom().getY(), mypaint);
	}

	void drawObject(Canvas canvas, Line line) {
		Paint mypaint = new Paint();
		mypaint.setColor(line.getColor());
		mypaint.setStrokeWidth(line.getSize() * getDensity());
		/*
		 * if (line.isArrow() == -1) { line.recalculation(); line.setArrow(1); }
		 */
		if (line.getFirstFigure().getPoint()
				.more(line.getSecondFigure().getPoint())
				&& line.isArrow() == 0)
			line.recalculation();
		Point first = null, second = null;
		Straight straight = pointToLine(line.getFirstFigure().getPoint(), line
				.getSecondFigure().getPoint());
		if (line.getFirstFigure().getClassName().equals("Ellipse")) {
			first = ellipseToPoint(straight, (Ellipse) line.getFirstFigure(),
					line.getSecondFigure().getPoint());
		} else {
			Rectangle rectangle = (Rectangle) line.getFirstFigure();
			Point[] points = new Point[4];
			points[0] = checkIntersection(rectangle.getLeftTop(),
					rectangle.getLeftBottom(),
					line.getFirstFigure().getPoint(), line.getSecondFigure()
							.getPoint());
			points[1] = checkIntersection(rectangle.getRightTop(),
					rectangle.getRightBottom(), line.getFirstFigure()
							.getPoint(), line.getSecondFigure().getPoint());
			points[2] = checkIntersection(rectangle.getLeftTop(),
					rectangle.getRightTop(), line.getFirstFigure().getPoint(),
					line.getSecondFigure().getPoint());
			points[3] = checkIntersection(rectangle.getLeftBottom(),
					rectangle.getRightBottom(), line.getFirstFigure()
							.getPoint(), line.getSecondFigure().getPoint());
			for (int i = 0; i < 4; i++) {
				if (points[i] != null) {
					first = points[i];
				}
			}
		}
		if (line.getSecondFigure().getClassName().equals("Ellipse")) {
			second = ellipseToPoint(straight, (Ellipse) line.getSecondFigure(),
					line.getFirstFigure().getPoint());
		} else {
			Rectangle rectangle = (Rectangle) line.getSecondFigure();
			Point[] points = new Point[4];
			points[0] = checkIntersection(rectangle.getLeftTop(),
					rectangle.getLeftBottom(),
					line.getFirstFigure().getPoint(), line.getSecondFigure()
							.getPoint());
			points[1] = checkIntersection(rectangle.getRightTop(),
					rectangle.getRightBottom(), line.getFirstFigure()
							.getPoint(), line.getSecondFigure().getPoint());
			points[2] = checkIntersection(rectangle.getLeftTop(),
					rectangle.getRightTop(), line.getFirstFigure().getPoint(),
					line.getSecondFigure().getPoint());
			points[3] = checkIntersection(rectangle.getLeftBottom(),
					rectangle.getRightBottom(), line.getFirstFigure()
							.getPoint(), line.getSecondFigure().getPoint());
			for (int i = 0; i < 4; i++) {
				if (points[i] != null) {
					second = points[i];
				}
			}
		}
		/*
		 * canvas.drawLine(line.getFirstFigure().getPoint().getX() +
		 * line.getFirstFigure().getRx(), line.getFirstFigure()
		 * .getPoint().getY(), line.getSecondFigure().getPoint().getX() -
		 * line.getSecondFigure().getRx(), line.getSecondFigure()
		 * .getPoint().getY(), paint);
		 */
		if (first != null && second != null) {
			canvas.drawLine(first.getX(), first.getY(), second.getX(),
					second.getY(), mypaint);
			if (line.isArrow() != 0) {
				double w = Math.abs(second.getX() - first.getX()) * 0.3;
				double h = Math.abs(second.getY() - first.getY()) * 0.3;
				if (h > w)
					w = h;
				if (w >= maxArrowSize)
					w = maxArrowSize;
				double angelLine = Math.atan2(first.getX() - second.getX(),
						first.getY() - second.getY());
				canvas.drawLine((float) second.getX(), (float) second.getY(),
						(float) (second.getX() + w * Math.sin(angelLine + 45)),
						(float) (second.getY() + w * Math.cos(angelLine + 45)),
						mypaint);
				canvas.drawLine((float) second.getX(), (float) second.getY(),
						(float) (second.getX() + w * Math.sin(angelLine - 45)),
						(float) (second.getY() + w * Math.cos(angelLine - 45)),
						mypaint);
			}
		}
	}

	void drawObject(Canvas canvas, Figure figure) {
		if (figure.getClassName().equals("Line")) {
			Line line = (Line) figure;
			if (line.getFirstFigure() == null)
				if (!line.setFirstFigure(model.isInsideAndNotLine(line
						.getLeftTop()))) {
					model.deleteFigure(line);
					return;
				}
			if (line.getSecondFigure() == null)
				if (!line.setSecondFigure(model.isInsideAndNotLine(line
						.getRightBottom()))) {
					model.deleteFigure(line);
					return;
				}
			drawObject(canvas, (Line) figure);
		} else if (figure.getClassName().equals("Ellipse"))
			drawObject(canvas, (Ellipse) figure);
		else if (figure.getClassName().equals("Rectangle"))
			drawObject(canvas, (Rectangle) figure);
	}

	void setTextWidth(Paint mypaint, Figure figure) {
		//Rect bounds = new Rect();
		int size = minTextSize;
		do {
			mypaint.setTextSize(++size);
			//bounds = new Rect();
			//paint.getTextBounds(figure.getText(), 0, 1, bounds);
		} while (mypaint.measureText(figure.getText()) < figure.getRx() * 1.6);
				//&& bounds.height() < figure.getRy());
	}

	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		this.canvas = canvas;
		// if (position.getX() != 0 && position.getY() != 0)
		// position.x = (int) (position.getX() * zoomWidth/zoom);
		// position.y = (int) (position.getY() * zoomWidth/zoom);
		canvas.translate(position.getX(), position.getY()); // ����������
		// �����
		canvas.scale(zoom / zoomWidth, zoom / zoomWidth); // ��������
		// ���������� �� ������

		/*
		 * Paint fullRectPaint = new Paint();
		 * fullRectPaint.setColor(Color.WHITE);
		 * fullRectPaint.setStyle(Paint.Style.FILL);
		 * canvas.drawRect(position.getX(), position.getY(),width,height,
		 * fullRectPaint);
		 */
		canvas.drawBitmap(image, 0, 0, paint); // ������ �����

		ListIterator<Figure> iter;
		iter = model.getIter();
		// paint.setAntiAlias(true);
		while (iter.hasNext()) {
			drawObject(canvas, iter.next());
		}
		// paint.setColor(Color.BLACK);
		for (int i = 0; i < track.trackRace.size() - 1; i++) {
			Point point = track.trackRace.get(i);
			Point point1 = track.trackRace.get(i + 1);
			// canvas.drawCircle(point.getX(),
			// point.getY(),paint.getStrokeWidth(), paint);
			canvas.drawLine(point.getX(), point.getY(), point1.getX(),
					point1.getY(), paint);
		}

		/*
		 * for (int i = 0; i < fingers.size(); i++) { // ���������� ��� �������
		 * // ���� ������ canvas.drawCircle(fingers.get(i).Now.getX(),
		 * fingers.get(i).Now.getY(), 20 * density, paint); }
		 */

		// paint.setTextSize(10);
		// canvas.drawText(text, 250, 150, paint);
	}

	int countOfFinger = 0;
	int rx = 0, ry = 0;
	private Point previousForFigure;

	public boolean onTouchEvent(MotionEvent e) {
		int index = e.getActionIndex();
		int id = e.getPointerId(index); // ������������� ������
		/*
		 * if (e.getAction() == MotionEvent.ACTION_POINTER_DOWN) {
		 * fingers.add(e.getActionIndex(), new Finger(id, (int) e.getX(), (int)
		 * e.getY())); /* movingFigure = model.isInsideAndNotLine(new
		 * Point((int) e.getX(), (int) e.getY())); if (movingFigure != null) {
		 * mode = 3; countOfFinger++; }
		 */
		// }
		int action = e.getActionMasked(); // ��������
		if (action == MotionEvent.ACTION_DOWN
				|| action == MotionEvent.ACTION_POINTER_DOWN) {
			fingers.add(index,
					new Finger(id, (int) ((e.getX(index) - position.getX())),
							(int) ((e.getY(index) - position.getY())), false));
			// handler.postDelayed(longPress, 1000);
			previous = new Point((int) ((e.getX(index) - position.getX())),
					(int) ((e.getY(index) - position.getY())));
			previousForFigure = new Point(
					(int) ((e.getX(index) - position.getX()) * (zoomWidth / zoom)),
					(int) ((e.getY(index) - position.getY()) * (zoomWidth / zoom)));
			setMovingFigure(model.isInsideAndNotLine(previousForFigure));
			// movingFigure = model.isBoundary(previous);
			if (getMovingFigure() != null) {
				setMode(2);
				rx = getMovingFigure().getRx();
				ry = getMovingFigure().getRy();
				// mode = 2;
				countOfFinger++;
			} else {
				setMode(1);
				if (drawingMode) {
					// mode = 1;
					track.addPoint(previousForFigure);
				}
			}
			invalidate();
		} else if (action == MotionEvent.ACTION_UP
				|| action == MotionEvent.ACTION_POINTER_UP) {
			Finger finger = fingers.get(index);
			if (finger != null) {
				if (System.currentTimeMillis() - finger.wasDown < 100
						&& finger.wasDown - lastTapTime < 200
						&& finger.wasDown - lastTapTime > 0
						&& checkDistance(finger.Now, lastTapPosition) < getDensity() * 25) {
					if (getMode() == 2) {
						/*
						 * Builder builder = new
						 * AlertDialog.Builder(getContext()); String[] items = {
						 * "�������", "������", "�����", "׸����", "�����",
						 * "Ƹ���" }; final AlertDialog dialog = builder
						 * .setTitle("�������� ���� ������") .setItems(items,
						 * new DialogInterface.OnClickListener() { public void
						 * onClick( DialogInterface dialog, int which) { int[]
						 * colors = { Color.RED, Color.GREEN, Color.BLUE,
						 * Color.BLACK, Color.WHITE, Color.YELLOW };
						 * getMovingFigure().setColor( colors[which]); }
						 * }).create(); dialog.show();
						 */
						AlertDialog.Builder alert = new AlertDialog.Builder(
								getContext());

						alert.setTitle("���������");
						alert.setMessage("���������");
						// ������� ���� �����
						final EditText input = new EditText(getContext());
						alert.setView(input);
						input.setText(getMovingFigure().getText());
						alert.setPositiveButton("��",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int whichButton) {
										String value = input.getText()
												.toString();
										// �������� �������� ��������� ������!
										getMovingFigure().setText(value);
										// canvas.drawText(value,
										// fingers.get(0).Now.getX(),
										// fingers.get(0).Now.getY(), paint);
									}
								});

						alert.setNegativeButton("������",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int whichButton) {
										// ���� ��������.
									}
								});

						alert.show();
					} else {
						/*
						 * Builder builder = new
						 * AlertDialog.Builder(getContext()); String[] items = {
						 * "�������", "������", "�����", "׸����", "�����",
						 * "Ƹ���" }; final AlertDialog dialog1 = builder
						 * .setTitle("�������� ���� �����") .setItems(items, new
						 * DialogInterface.OnClickListener() { public void
						 * onClick( DialogInterface dialog, int which) { int[]
						 * colors = { Color.RED, Color.GREEN, Color.BLUE,
						 * Color.BLACK, Color.WHITE, Color.YELLOW };
						 * paint.setColor(colors[which]); } }).create();
						 * dialog1.show();
						 */
						new ColorPickerDialog(getContext(),
								new ColorPickerDialog.OnColorChangedListener() {
									public void colorChanged(int color) {
										paint.setColor(color);
									}
								}, paint.getColor()).show();
					}
				}
				lastTapTime = System.currentTimeMillis();
				lastTapPosition = finger.Now;
				fingers.remove(finger); // �������
				setMode(0);
			}
			// �����,
			// ������� ���
			// �������
			if (drawingMode) {
				recognizer = new Recognizer(1);
				if (track.trackRace.size() > 3) {
					Figure figure = recognizer.recognize(track);
					if (figure != null) {
						if (figure.getClassName().equals("Line")) {
							Line line = (Line) figure;
							if (line.isHatching() != 0) {
								line.setFirstFigure(model
										.isInsideAndNotLine(new Point(line
												.getPoint().getX(), line
												.getPoint().getY())));
								if (line.getFirstFigure() != null) {
									model.deleteFigure(line.getFirstFigure());
								} else {
									line.setFirstFigure(model.isInsideLine(
											new Point(line.getPoint().getX()
													- line.getRx(), line
													.getPoint().getY()
													- line.getRy()), new Point(
													line.getPoint().getX()
															+ line.getRx(),
													line.getPoint().getY()
															+ line.getRy())));
									if (line.getFirstFigure() != null) {
										model.deleteFigure(line
												.getFirstFigure());
									}
								}
								track = new Track();
								invalidate();
								return true;
							}
						}
						figure.setColor(paint.getColor());
						model.addFigure(figure);
					}
				}
				track = new Track();
			} else {
				countOfFinger--;
				setMode(0);
			}
			invalidate();
		} else if (action == MotionEvent.ACTION_MOVE) {
			for (int n = 0; n < fingers.size(); n++) { // ���������
				// ���������
				// ���� �������
				fingers.get(n).setNow((int) ((e.getX(n) - position.getX())),
						(int) ((e.getY(n) - position.getY())));
			}
			Point point = new Point((int) ((e.getX(0) - position.getX())),
					(int) ((e.getY(0) - position.getY())));
			Point pointForFigure = new Point(
					(int) ((e.getX(0) - position.getX()) * (zoomWidth / zoom)),
					(int) ((e.getY(0) - position.getY()) * (zoomWidth / zoom)));
			if (drawingMode) {
				track.addPoint(pointForFigure);
			} else {
				if (getMode() == 2) {
					if (fingers.size() >= 2) {
						int yDis, xDis;
						yDis = Math.abs(fingers.get(0).Now.getY()
								- fingers.get(1).Now.getY())
								- Math.abs(fingers.get(0).Before.getY()
										- fingers.get(1).Before.getY());
						xDis = Math.abs(fingers.get(0).Now.getX()
								- fingers.get(1).Now.getX())
								- Math.abs(fingers.get(0).Before.getX()
										- fingers.get(1).Before.getX());
						Figure fig = getMovingFigure();
						int i = model.figureList.indexOf(fig);
						if (rx + xDis > 5) {
							while (rx + xDis + fig.getPoint().getX() > bitMapWidth)
								xDis = xDis - 1;
							fig.setRx(rx + xDis);
						} else
							fig.setRx(5);
						if (ry + yDis > 5) {
							while (ry + yDis + fig.getPoint().getY() > bitMapWidth)
								yDis = yDis - 1;
							fig.setRy(ry + yDis);
						} else
							fig.setRy(5);
						rx = fig.getRx();
						ry = fig.getRy();
						model.figureList.set(i, fig);
					} else {
						Figure fig = getMovingFigure();
						int i = model.figureList.indexOf(fig);
						fig.move(new Point(fig.getPoint().getX()
								+ (pointForFigure.getX() - previousForFigure
										.getX()), fig.getPoint().getY()
								+ (pointForFigure.getY() - previousForFigure
										.getY())));
						model.figureList.set(i, fig);
						previousForFigure = new Point(pointForFigure.getX(),
								pointForFigure.getY());
					}
				} else {
					if (fingers.size() >= 2) {
						float now = checkDistance(fingers.get(0).Now,
								fingers.get(1).Now);
						float before = checkDistance(fingers.get(0).Before,
								fingers.get(1).Before);
						float oldSize = zoom;
						// zoom = now - before + zoom;
						zoom = Math.max(now - before + zoom, getDensity() * 50);
						position.x -= (zoom - oldSize) / 2;
						position.y -= (zoom - oldSize) / 2;
					} else {
						position.x += point.getX() - previous.getX();
						position.y += point.getY() - previous.getY();
						// previous = new Point(point.getX(), point.getY());
						// previous = new Point(point.getX(), point.getY());
					}
				}
			}
			invalidate();
		}
		/*
		 * int action = e.getActionMasked(); // �������� if(action ==
		 * MotionEvent.ACTION_DOWN || action == MotionEvent.ACTION_POINTER_DOWN)
		 * fingers.add(e.getActionIndex(), new Finger(id, (int)e.getX(),
		 * (int)e.getY())); else if(action == MotionEvent.ACTION_UP || action ==
		 * MotionEvent.ACTION_POINTER_UP)
		 * fingers.remove(fingers.get(e.getActionIndex())); // ������� �����,
		 * ������� ��� ������� else if(action == MotionEvent.ACTION_MOVE){
		 * for(int n = 0; n < fingers.size(); n++){ // ��������� ��������� ����
		 * ������� fingers.get(n).setNow((int)e.getX(n), (int)e.getY(n)); } }
		 * invalidate();
		 */
		return true;
	}

	static float checkDistance(Point p1, Point p2) {
		return FloatMath.sqrt((p1.x - p2.x) * (p1.x - p2.x) + (p1.y - p2.y)
				* (p1.y - p2.y));
	}

	/*
	 * Runnable longPress = new Runnable() { public void run() {
	 * if(fingers.size() > 0 && fingers.get(0).enabledLongTouch) {
	 * fingers.get(0).enabledLongTouch = false; drawingMode = !drawingMode; //
	 * vibrator.vibrate(80); } } };
	 */
	public int getMode() {
		return mode;
	}

	public void setMode(int mode) {
		this.mode = mode;
	}

	public Straight pointToLine(Point point1, Point point2) {
		// ����������� ��������� ������ �� ����������� ���� �����}
		Straight straight = new Straight(point2.getY() - point1.getY(),
				point1.getX() - point2.getX(), -point1.getX()
						* (point2.getY() - point1.getY()) + point1.getY()
						* (point2.getX() - point1.getX()));
		/*
		 * a:= y2-y1; b:= x1-x2; c:= -x1*(y2-y1)+y1*(x2-x1);
		 */
		return straight;
	}

	public Point lineToPoint(Straight straight1, Straight straight2) {
		/*
		 * ����������� ��������� ����� ����������� ���� �����.
		 */
		double d;
		d = straight1.getA() * straight2.getB() - straight1.getB()
				* straight2.getA();// a1*b2-b1*a2;
		if (d != 0) {
			double dx = -straight1.getC() * straight2.getB() + straight1.getB()
					* straight2.getC();
			double dy = -straight1.getA() * straight2.getC() + straight1.getC()
					* straight2.getA();
			/*
			 * dx:=-c1*b2+b1*c2; dy:=-a1*c2+c1*a2;
			 */
			double x = dx / d;
			double y = dy / d;
			return new Point((int) x, (int) y);
		} else
			return null;
	}

	public Point checkIntersection(Point a1, Point a2, Point b1, Point b2) {
		double d, da, db, ta, tb;
		d = (a1.getX() - a2.getX()) * (b2.getY() - b1.getY())
				- (a1.getY() - a2.getY()) * (b2.getX() - b1.getX());
		da = (a1.getX() - b1.getX()) * (b2.getY() - b1.getY())
				- (a1.getY() - b1.getY()) * (b2.getX() - b1.getX());
		db = (a1.getX() - a2.getX()) * (a1.getY() - b1.getY())
				- (a1.getY() - a2.getY()) * (a1.getX() - b1.getX());
		if (Math.abs(d) < 0.000001)
			return null;
		else {
			ta = da / d;
			tb = db / d;
			if ((0 <= ta) && (ta <= 1) && (0 <= tb) && (tb <= 1)) {
				Point c = new Point((int) (a1.getX() + ta
						* (a2.getX() - a1.getX())), (int) (a1.getY() + ta
						* (a2.getY() - a1.getY())));
				return c;
			} else
				return null;
		}
	}

	public Point ellipseToPoint(Straight straight, Ellipse ellipse,
			Point secondFigureCenter) {
		double a, b, c, k;
		k = -straight.getA() / straight.getB();
		a = 1d / ((double) (ellipse.getRx() * (double) ellipse.getRx())) + k
				* k / ((double) (ellipse.getRy() * (double) ellipse.getRy()));
		b = -(2d * (double) ellipse.getPoint().getX())
				/ ((double) ellipse.getRx() * (double) ellipse.getRx())
				+ (2d * k * (-straight.getC() / straight.getB() - (double) ellipse
						.getPoint().getY()))
				/ ((double) ellipse.getRy() * (double) ellipse.getRy());
		c = +((double) ellipse.getPoint().getX() * (double) ellipse.getPoint()
				.getX())
				/ ((double) ellipse.getRx() * (double) ellipse.getRx())
				+ (-straight.getC() / straight.getB() - (double) ellipse
						.getPoint().getY())
				* (-straight.getC() / straight.getB() - (double) ellipse
						.getPoint().getY())
				/ ((double) ellipse.getRy() * (double) ellipse.getRy()) - 1d;
		double D = (b * b - 4f * a * c);
		if (D > 0) {
			double x1 = (-b + (double) Math.sqrt(D)) / (2d * a);
			double x2 = (-b - (double) Math.sqrt(D)) / (2d * a);
			double y1 = k * x1 - straight.getC() / straight.getB();
			double y2 = k * x2 - straight.getC() / straight.getB();
			double d1 = (double) Math
					.sqrt(((double) secondFigureCenter.getX() - x1)
							* ((double) secondFigureCenter.getX() - x1)
							+ ((double) secondFigureCenter.getY() - y1)
							* ((double) secondFigureCenter.getY() - y1));
			double d2 = (double) Math.sqrt((secondFigureCenter.getX() - x2)
					* ((double) secondFigureCenter.getX() - x2)
					+ ((double) secondFigureCenter.getY() - y2)
					* ((double) secondFigureCenter.getY() - y2));
			if (d1 < d2)
				return new Point((int) x1, (int) y1);
			else
				return new Point((int) x2, (int) y2);
		} else
			return null;
	}

	public static float getDensity() {
		return density;
	}

	public void setDensity(float density) {
		this.density = density;
	}
}
