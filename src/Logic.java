import com.company.util.ArrayUtils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Arrays;


public class Logic {
    public static void getResult(String fileNameInput, String fileNameOutput) throws Exception {
        int[][] matrix = ArrayUtils.readIntArray2FromFile(fileNameInput);

        checkIfArrayIsNull(matrix);
        checkIfArrayIsCorrect(matrix);
        checkIfArrayIsSquare(matrix);

        double[][] inverseMatrix = solveMatrix(matrix);
        double[][] check = multiplyMatrixMatrix(matrix, inverseMatrix);
        writeIntoFile(matrix, inverseMatrix, check, fileNameOutput);
    }

    public static double[][] solveMatrix(int[][] mat){
        return multiplyMatrixNumber(getTransposedMatrix(getTempMatrix(mat)), (double) 1/solveDeterminant(mat));
    }

    public static int solveDeterminant(int[][] det){
        if (det.length == 1){
            return det[0][0];
        }
        if (det.length == 2){
            return det[0][0]*det[1][1] - det[0][1]*det[1][0];
        }
        if (det.length == 3){
            return det[0][0]*det[1][1]*det[2][2]+det[0][1]*det[1][2]*det[2][0]+det[1][0]*det[0][2]*det[2][1]
                    -det[0][2]*det[1][1]*det[2][0]-det[0][1]*det[1][0]*det[2][2]-det[0][0]*det[1][2]*det[2][1];
        }

        int sum = 0;
        for (int i = 0; i < det.length; i++){
            sum += Math.pow(-1, i+1+1) * det[i][0] * solveDeterminant(getMinor(det, i, 0));
        }

        return sum;
    }


    public static int[][] getTempMatrix(int[][] mat){
        int[][] tempMatrix = new int[mat.length][mat.length];

        for (int i = 0; i< mat.length; i++) {
            for (int j = 0; j < mat.length; j++) {
                tempMatrix[i][j] = (int) (Math.pow(-1, i+j+2) * solveDeterminant(getMinor(mat, i, j)));
            }
        }

        return tempMatrix;
    }

    public static int[][] getTransposedMatrix(int[][] mat){
        int[][] transposedMatrix = new int[mat.length][mat.length];

        for(int i=0; i<mat.length; i++) {
            for(int j=0; j<mat[i].length; j++) {
                transposedMatrix[j][i] = mat[i][j];
            }
        }

        return transposedMatrix;
    }

    public static int[][] getMinor(int[][] matrix, int rowNumber, int columnNumber){
        int[][] minor = new int [matrix.length - 1][matrix.length - 1];
        int r = 0;
        for (int i = 0; i < matrix.length; i++){
            if (i == rowNumber){
                continue;
            }

            int c = 0;
            for (int j = 0; j < matrix.length; j++){
                if (j == columnNumber){
                    continue;
                }
                minor[r][c] = matrix[i][j];

                c++;
            }

            r++;
        }

        return minor;
    }

    public static double[][] multiplyMatrixNumber(int[][] mat, int num){
        double[][] newMat = new double[mat.length][mat.length];
        for (int i = 0; i< mat.length; i++){
            for (int j = 0; j < mat.length; j++){
                newMat[i][j] = mat[i][j] * num;
            }
        }

        return newMat;
    }

    public static double[][] multiplyMatrixNumber(int[][] mat, double num){
        double[][] newMat = new double[mat.length][mat.length];
        for (int i = 0; i< mat.length; i++){
            for (int j = 0; j < mat.length; j++){
                newMat[i][j] = mat[i][j] * num;
            }
        }

        return newMat;
    }

    public static double[][] multiplyMatrixMatrix(int[][] mat1, double[][] mat2){
        double[][] res = new double[mat1.length][mat1.length];

        for (int i = 0; i < res.length; i++){
            for (int j = 0; j < res.length; j++){
                res[i][j] = multiplyCell(mat1, mat2, i, j);
            }
        }

        return res;
    }

    public static int multiplyCell(int[][] mat1, double[][] mat2, int row, int col){
        int cell = 0;
        for (int i = 0; i < mat2.length; i++){
            cell += mat1[row][i] * mat2[i][col];
        }

        return cell;
    }

    public static void writeIntoFile(int[][] orig, double[][] solved, double[][] check, String fileName) throws Exception{
        printMatrix(orig, fileName);
        printMatrix(solved, fileName);
        printMatrix(check, fileName);
    }

    public static void printMatrix(int[][] mat, String fileName) throws Exception{
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
        for (int[] line : mat){
            writer.write(Arrays.toString(line) + "\n");
        }
        writer.write("\n");
        writer.flush();
    }


    public static void printMatrix(double[][] mat, String fileName) throws Exception{
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
        for (double[] line : mat){
            writer.write(Arrays.toString(line));
        }
        writer.write(" ");
        writer.flush();
    }

    public static void checkIfArrayIsNull(int [][] array) throws Exception{
        if (array == null){
            throw new Exception("Отсутствует input файл");
        }
    }

    public static void checkIfArrayIsCorrect (int [][] array) throws Exception {
        if (array.length < 1 || array[0].length < 1) {
            throw new Exception("Input файл пустой");
        }
    }

    public static void checkIfArrayIsSquare (int [][] array) throws Exception {
        for (int[] line : array) {
            if (line.length != array.length) {
                throw new Exception("Матрица должна быть квадратной");
            }
        }
    }


}
