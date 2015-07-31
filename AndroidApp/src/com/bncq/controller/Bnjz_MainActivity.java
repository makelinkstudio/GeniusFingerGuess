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
		// ������xml����activity����
		setContentView(R.layout.bnjz_main);
		// ��ʼ��Ϸ
		Button btn_new_game = (Button) findViewById(R.id.btn_new_game);
		btn_new_game.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				newGame();
			}
		});

		// �����ƶ�
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
		// �����ƶ�
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
		// �����ƶ�
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
		// �����ƶ�
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

	// ��������
	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {

		// System.out.println("����");
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

	// ���ؽǶ�
	private double GetSlideAngle(float dx, float dy) {
		return Math.atan2(dy, dx) * 180 / Math.PI;
	}

	// ���������յ㷵�ط��� 1�����ϣ�2�����£�3������4������,0��δ����
	private void GetSlideDirection(float startX, float startY, float endX,
			float endY) {
		float dy = startY - endY;
		float dx = endX - startX;
		// �����������̫��
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

	// ��ʼ������
	public void init() {
		// ��ʼ������
		cleanMatrix();
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				matrix[i][j] = 0;
			}
		}
	}

	// ����Ϸ
	public void newGame() {
		init();
		generateOneNumber();
		generateOneNumber();
	}

	// ��ȡR.id����
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

	// ������о����
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

	// ��������λ�� ����ȭ���ȼ����ɾ����
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

	// �������һ���������
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
		// �������һ����ȭ
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

	// ˢ�¾������ͼ
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

	// �����ƶ�
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

	// �����ƶ�
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

	// �����ƶ�
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

	// �����ƶ�
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
