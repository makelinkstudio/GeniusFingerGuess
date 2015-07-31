package com.bncq.common;

import android.R.bool;
import android.R.integer;

public class BnjzSupport {
	// 判断是否有空位
	public boolean noSpace(int[][] matrix) {
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				if (matrix[i][j] == 0) {
					return false;
				}
			}
		}
		return true;
	}
	//判断是否可以升级
	public boolean canUpgrade(int fromMatrix,int toMatrix) {
		int formMatrixLevel = fromMatrix % 10;
		int formMatrixPunch = fromMatrix / 10;
		int toMatrixLevel = toMatrix % 10;
		int toMatrixPunch = toMatrix / 10;
		if(formMatrixLevel != toMatrixLevel){
			return false;
		}
		if(formMatrixLevel == 5 || toMatrixLevel == 5){
			return false;
		}
		switch (formMatrixPunch) {
		case 0:
			if(toMatrixPunch != 0){
				return true;
			}
			break;
		case 2:
			if(toMatrixPunch != 2){
				return true;
			}
			break;
		case 5:
			if(toMatrixPunch != 5){
				return true;
			}
		default:
			break;
		}
		return false;
	}
	
	//判断是否可以水平移动
	public boolean noBlockHorizontal(int row,int col1,int col2,int[][] matrix) {
		for(int i = col1 + 1;i < col2;i++){
			if(matrix[row][i] != 0){
				return false;
			}
		}
		return true;
	}
	
	//判断是否可以垂直移动
	public boolean noBlockVertical(int col,int row1,int row2,int[][] matrix) {
		for(int i = row1 + 1;i < row2 ;i++){
			if(matrix[i][col] != 0){
				return false;
			}
		}
		return true;
	}
	
	//升级
	public int upGrade(int fromMatrix,int toMatrix) {
		int fromMatrixLevel = fromMatrix % 10;
		int fromMatrixPunch = fromMatrix / 10;
		int toMatrixLevel = toMatrix % 10;
		int toMatrixPunch = toMatrix / 10;
		int newMatrixLevel = 0;
		int newMatrixPunch = 0 ;
		int newMatrix;
		switch (fromMatrixLevel) {
		case 1:
			newMatrixLevel = 2;
			break;
		case 2:
			 newMatrixLevel = 3;
			break;
		case 3:
			 newMatrixLevel = 4;
			break;
		case 4:
			 newMatrixLevel = 5;
			break;
		default:
			break;
		}
		switch (fromMatrixPunch) {
		case 0:
			if(toMatrixPunch == 5){
				newMatrixPunch = 5;
			}else{
				newMatrixPunch = 0;
			}
			break;
		case 2:
			if(toMatrixPunch == 0){
				newMatrixPunch = 0;
			}else{
				newMatrixPunch = 2;
			}
			break;
		case 5:
			if(toMatrixPunch == 2){
				newMatrixPunch = 2;
			}else{
				newMatrixPunch = 5;
			}
			break;
		default:
			break;
		}
		return newMatrix = newMatrixPunch * 10 + newMatrixLevel;
	}
	
	//判断是否能向左移动
	public boolean canMoveLeft(int[][] matrix) {
		for(int i = 0;i < 4;i++){
			for(int j = 1;j < 4;j++){
				if(matrix[i][j] != 0){
					if(matrix[i][j-1] == 0 || canUpgrade(matrix[i][j-1],matrix[i][j])){
						return true;
					}
				}
			}
		}
		return false;
	}
	
	//判断是否能向上移动
	public boolean canMoveUp(int[][] matrix) {
		for(int i = 1;i < 4;i++){
			for(int j = 0;j < 4;j++){
				if(matrix[i][j] != 0){
					if(matrix[i-1][j] ==0 || canUpgrade(matrix[i-1][j],matrix[i][j])){
						return true;
					}
				}
			}
		}
		return false;
	}
	
	//判断是否能向右移动
	public boolean canMoveRight(int[][] matrix) {
		for(int i = 3;i > -1;i--){
			for(int j = 2;j > -1;j--){
				if(matrix[i][j] != 0){
					if(matrix[i][j+1] == 0 || canUpgrade(matrix[i][j+1],matrix[i][j])){
						return true;
					}
				}
			}
		}
		return false;
	}
	
	//判断是否能向下移动
	public boolean canMoveDown(int[][] matrix) {
		for(int i = 2;i > -1;i--){
			for(int j = 0;j < 4;j++){
				if(matrix[i][j] != 0){
					if(matrix[i+1][j] == 0 || canUpgrade(matrix[i+1][j],matrix[i][j])){
						return true;
					}
				}
			}
		}
		return false;
	}
}
