package com.bncq.controller;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.Random;





import com.bncq.R;
import com.bncq.common.BnjzSupport;

import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.R.integer;
import android.app.Activity;
import android.os.Bundle;

public class Bnjz_MainActivity extends Activity {
	private int matrix[][] = new int[4][4];
	private BnjzSupport support;
	float startX = 0, startY = 0, endX, endY;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 将布局xml引入activity引入
		setContentView(R.layout.bnjz_main);
		// 开始游戏
		Button btn_new_game = (Button) findViewById(R.id.btn_new_game);
		btn_new_game.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				newGame();
			}
		});

		// 向左移动
		Button btn_leftButton = (Button) findViewById(R.id.btn_left);
		btn_leftButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (moveLeft()) {
					generateOneNumber();
				}
			}
		});
		// 向上移动
		Button btn_upButton = (Button) findViewById(R.id.btn_up);
		btn_upButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (moveUp()) {
					generateOneNumber();
				}
			}
		});
		// 向右移动
		Button btn_rightButton = (Button) findViewById(R.id.btn_right);
		btn_rightButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (moveRight()) {
					generateOneNumber();
				}
			}
		});
		// 向下移动
		Button btn_downButton = (Button) findViewById(R.id.btn_down);
		btn_downButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (moveDown()) {
					generateOneNumber();
				}
			}
		});
		support = new BnjzSupport();
		init();
		newGame();
	}

	// 触摸控制
	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {

		// System.out.println("触摸");
		int action = event.getAction();
		if (action == MotionEvent.ACTION_DOWN) {
			startX = event.getX();
			startY = event.getY();
		} else if (action == MotionEvent.ACTION_UP) {
			endX = event.getX();
			endY = event.getY();
			GetSlideDirection(startX, startY, endX, endY);
			// System.out.println(startX+","+startY+"|"+endX+","+endY+"  "+direction);
			// Toast.makeText(this, direction+"", Toast.LENGTH_LONG).show();
		}
		return super.dispatchTouchEvent(event);
	}

	// 返回角度
	private double GetSlideAngle(float dx, float dy) {
		return Math.atan2(dy, dx) * 180 / Math.PI;
	}

	// 根据起点和终点返回方向 1：向上，2：向下，3：向左，4：向右,0：未滑动
	private void GetSlideDirection(float startX, float startY, float endX,
			float endY) {
		float dy = startY - endY;
		float dx = endX - startX;
		// 如果滑动距离太短
		if (Math.abs(dx) < 2 && Math.abs(dy) < 2) {
			
		}
		double angle = GetSlideAngle(dx, dy);
		if (angle >= -45 && angle < 45) {
			if (moveRight()) {
				generateOneNumber();
			}
		} else if (angle >= 45 && angle < 135) {
			if (moveUp()) {
				generateOneNumber();
			}
		} else if (angle >= -135 && angle < -45) {
			if (moveDown()) {
				generateOneNumber();
			}
		} else if ((angle >= 135 && angle <= 180)
				|| (angle >= -180 && angle < -135)) {
			if (moveLeft()) {
				generateOneNumber();
			}
		}
	}

	// 初始化方法
	public void init() {
		// 初始化矩阵
		cleanMatrix();
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				matrix[i][j] = 0;
			}
		}
	}

	// 新游戏
	public void newGame() {
		init();
		generateOneNumber();
		generateOneNumber();
	}

	// 获取R.id方法
	public int getRId(String obj) {
		int index = 0;
		try {
			Field field = R.id.class.getField(obj);
			index = field.getInt(new R.id());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return index;
	}

	// 清空所有矩阵块
	public void cleanMatrix() {
		String matrix_cell[][] = new String[4][4];
		String matrix_cell_punch[][] = new String[4][4];
		String matrix_cell_level[][] = new String[4][4];
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				matrix_cell[i][j] = "matrix_cell_" + i + "_" + j;
				matrix_cell_punch[i][j] = "matrix_cell_punch_" + i + "_" + j;
				matrix_cell_level[i][j] = "matrix_cell_level_" + i + "_" + j;

				LinearLayout matrix_cell_show = (LinearLayout) findViewById(getRId(matrix_cell[i][j]));
				ImageView matrix_cell_punch_show = (ImageView) findViewById(getRId(matrix_cell_punch[i][j]));
				ImageView matrix_cell_level_show = (ImageView) findViewById(getRId(matrix_cell_level[i][j]));
				matrix_cell_show
						.setBackgroundResource(R.drawable.matrix_cell_none);
				matrix_cell_punch_show
						.setBackgroundResource(R.drawable.none_punch);
				matrix_cell_level_show
						.setBackgroundResource(R.drawable.none_level);
			}
		}
	}

	// 根据输入位置 、出拳、等级生成矩阵块
	public void showMatrix(int i, int j, int punch, int level) {
		String matrix_cell = "matrix_cell_" + i + "_" + j;
		String matrix_cell_punch = "matrix_cell_punch_" + i + "_" + j;
		String matrix_cell_level = "matrix_cell_level_" + i + "_" + j;
		LinearLayout matrix_cell_show = (LinearLayout) findViewById(getRId(matrix_cell));
		ImageView matrix_cell_punch_show = (ImageView) findViewById(getRId(matrix_cell_punch));
		ImageView matrix_cell_level_show = (ImageView) findViewById(getRId(matrix_cell_level));
		switch (level) {
		case 1:
			matrix_cell_show.setBackgroundResource(R.drawable.matrix_cell_l1);
			matrix_cell_level_show.setBackgroundResource(R.drawable.one);
			break;
		case 2:
			matrix_cell_show.setBackgroundResource(R.drawable.matrix_cell_l2);
			matrix_cell_level_show.setBackgroundResource(R.drawable.two);
			break;
		case 3:
			matrix_cell_show.setBackgroundResource(R.drawable.matrix_cell_l3);
			matrix_cell_level_show.setBackgroundResource(R.drawable.three);
			break;
		case 4:
			matrix_cell_show.setBackgroundResource(R.drawable.matrix_cell_l4);
			matrix_cell_level_show.setBackgroundResource(R.drawable.four);
			break;
		case 5:
			matrix_cell_show.setBackgroundResource(R.drawable.matrix_cell_l5);
			matrix_cell_level_show.setBackgroundResource(R.drawable.five);
			break;
		default:
			break;
		}
		switch (punch) {
		case 0:
			matrix_cell_punch_show.setBackgroundResource(R.drawable.shi);
			break;
		case 2:
			matrix_cell_punch_show.setBackgroundResource(R.drawable.jian);
			break;
		case 5:
			matrix_cell_punch_show.setBackgroundResource(R.drawable.bao);
			break;
		default:
			break;
		}
	}

	// 随机生成一个方块矩阵
	public boolean generateOneNumber() {
		if (support.noSpace(this.matrix)) {
			return false;
		}
		Random randNum = new Random();
		int randX = randNum.nextInt(4);
		int randY = randNum.nextInt(4);
		int time = 0;
		while (time < 50) {
			if (this.matrix[randX][randY] == 0) {
				break;
			} else {
				randX = randNum.nextInt(4);
				randY = randNum.nextInt(4);
				time = time++;
			}
		}
		if (time == 50) {
			for (int i = 0; i < 4; i++) {
				for (int j = 0; j < 4; j++) {
					if (this.matrix[i][j] == 0) {
						randX = i;
						randY = j;
					}
				}
			}
		}
		// 随机生成一个出拳
		int randPunch = randNum.nextInt(3);
		if (randPunch == 0) {
			randPunch = 0;
		} else if (randPunch == 1) {
			randPunch = 2;
		} else {
			randPunch = 5;
		}
		this.matrix[randX][randY] = (randPunch * 10) + 1;
		showMatrix(randX, randY, randPunch, 1);
		return true;
	}

	// 刷新矩阵块视图
	public void showMatrix() {
		int punch[][] = new int[4][4];
		int level[][] = new int[4][4];
		String matrix_cell[][] = new String[4][4];
		String matrix_cell_punch[][] = new String[4][4];
		String matrix_cell_level[][] = new String[4][4];
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				if (matrix[i][j] != 0) {
					punch[i][j] = this.matrix[i][j] / 10;
					level[i][j] = this.matrix[i][j] % 10;
					showMatrix(i, j, punch[i][j], level[i][j]);
				} else {
					matrix_cell[i][j] = "matrix_cell_" + i + "_" + j;
					matrix_cell_punch[i][j] = "matrix_cell_punch_" + i + "_"
							+ j;
					matrix_cell_level[i][j] = "matrix_cell_level_" + i + "_"
							+ j;
					LinearLayout matrix_cell_show = (LinearLayout) findViewById(getRId(matrix_cell[i][j]));
					ImageView matrix_cell_punch_show = (ImageView) findViewById(getRId(matrix_cell_punch[i][j]));
					ImageView matrix_cell_level_show = (ImageView) findViewById(getRId(matrix_cell_level[i][j]));
					matrix_cell_show
							.setBackgroundResource(R.drawable.matrix_cell_none);
					matrix_cell_punch_show
							.setBackgroundResource(R.drawable.none_punch);
					matrix_cell_level_show
							.setBackgroundResource(R.drawable.none_level);
				}
			}
		}
	}

	// 向左移动
	public boolean moveLeft() {
		if (!support.canMoveLeft(this.matrix)) {
			return false;
		}
		// moveLeft
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				if (this.matrix[i][j] != 0) {
					for (int k = 0; k < j; k++) {
						if (this.matrix[i][k] == 0
								&& support.noBlockHorizontal(i, k, j,
										this.matrix)) {
							this.matrix[i][k] = this.matrix[i][j];
							this.matrix[i][j] = 0;
							continue;
						} else if (support.noBlockHorizontal(i, k, j,
								this.matrix)
								&& support.canUpgrade(this.matrix[i][j],
										this.matrix[i][k])) {
							this.matrix[i][k] = support.upGrade(
									this.matrix[i][j], this.matrix[i][k]);
							this.matrix[i][j] = 0;
							continue;
						}
					}
				}
			}
		}
		showMatrix();
		return true;
	}

	// 向上移动
	public boolean moveUp() {
		if (!support.canMoveUp(this.matrix)) {
			return false;
		}
		// moveUp
		for (int i = 1; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				if (this.matrix[i][j] != 0) {
					for (int k = 0; k < i; k++) {
						if (this.matrix[k][j] == 0
								&& support
										.noBlockVertical(j, k, i, this.matrix)) {
							// move
							this.matrix[k][j] = this.matrix[i][j];
							this.matrix[i][j] = 0;
							continue;
						} else if (support
								.noBlockVertical(j, k, i, this.matrix)
								&& support.canUpgrade(this.matrix[i][j],
										this.matrix[k][j])) {
							this.matrix[k][j] = support.upGrade(
									this.matrix[i][j], this.matrix[k][j]);
							this.matrix[i][j] = 0;
							continue;
						}
					}
				}
			}
		}
		showMatrix();
		return true;
	}

	// 向右移动
	public boolean moveRight() {
		if (!support.canMoveRight(this.matrix)) {
			return false;
		}
		// moveRight
		for (int i = 0; i < 4; i++) {
			for (int j = 2; j > -1; j--) {
				if (this.matrix[i][j] != 0) {
					for (int k = 3; k > j; k--) {
						if (this.matrix[i][k] == 0
								&& support.noBlockHorizontal(i, j, k,
										this.matrix)) {
							this.matrix[i][k] = this.matrix[i][j];
							this.matrix[i][j] = 0;
							continue;
						} else if (support.noBlockHorizontal(i, j, k,
								this.matrix)
								&& support.canUpgrade(this.matrix[i][j],
										this.matrix[i][k])) {
							this.matrix[i][k] = support.upGrade(
									this.matrix[i][j], this.matrix[i][k]);
							this.matrix[i][j] = 0;
							continue;
						}
					}
				}
			}
		}
		showMatrix();
		return true;
	}

	// 向下移动
	public boolean moveDown() {
		if (!support.canMoveDown(this.matrix)) {
			return false;
		}
		// moveDown
		for (int i = 2; i > -1; i--) {
			for (int j = 0; j < 4; j++) {
				if (this.matrix[i][j] != 0) {
					for (int k = 3; k > i; k--) {
						if (this.matrix[k][j] == 0
								&& support
										.noBlockVertical(j, i, k, this.matrix)) {
							this.matrix[k][j] = this.matrix[i][j];
							this.matrix[i][j] = 0;
							continue;
						} else if (support
								.noBlockVertical(j, i, k, this.matrix)
								&& support.canUpgrade(this.matrix[i][j],
										this.matrix[k][j])) {
							this.matrix[k][j] = support.upGrade(
									this.matrix[i][j], this.matrix[k][j]);
							this.matrix[i][j] = 0;
							continue;
						}
					}
				}
			}
		}
		showMatrix();
		return true;
	}
}
