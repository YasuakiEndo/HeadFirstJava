package head.first.java.chapter06;

import java.io.*;
import java.util.ArrayList;

public class GameHelper {
	private static final String alphabet = "abcdef";
	private int gridLength = 7;
	private int gridSize = 49;
	private int[] grid = new int[gridSize];
	private int comCount = 0;

	public String getUserInput(String prompt) {
		String inputLine = null;
		System.out.println(prompt + " ");
		try {
			BufferedReader is = new BufferedReader(new InputStreamReader(System.in));
			inputLine = is.readLine();
			if (inputLine.length() == 0) {
				return null;
			}
		} catch (IOException e) {
			System.out.println("IoException: " + e);
		}
		return inputLine.toLowerCase();
	}

	public ArrayList<String> placeDotCom(int comSize) {
		ArrayList<String> alphaCells = new ArrayList<String>();
//		String[] alphacoords = new String[comSize]; // 'f6'などのコードを保持する
		String temp		= null;				// 後で連結する文字列を一時的に保持
		int[] coords	= new int[comSize];	// 候補となるセル番号を保持する
		int attempts	= 0;				// 試行回数のカウンタ
		boolean sucess	= false;			// 配置が適切かを示すフラグ
		int location	= 0;				// その時点でどのセルが検討対象になっているかを示す

		comCount++;					// 配置対象のDotComオブジェクトを変える
		int incr = 1;				// オブジェクトの配置を横向きにする
		if ((comCount % 2) == 1) {	// 奇数版目のDotComオブジェクトの場合は
			incr = gridLength;		// オブジェクトの配置を縦向きにする
		}

		while (!sucess & attempts++ < 200) {						// メインのループ
			location = (int) (Math.random() * gridSize);			// 乱数を作る
//			System.out.print(" try " + location);
			int x = 0;												// いくつ目のセルか、を示す値
			sucess = true;											// trueは番号の指定が成功したことを示す
			while (sucess && x < comSize) {							// 隣から使われていない場所を探す
				if (grid[location] == 0) {							// まだ使われていないかどうかを判定
					coords[x++] = location;							// 得られた配列情報を保存
					location += incr;								// 処理対象を隣のセルに移す
					if (location >= gridSize) {						// グリッドから下にはみ出ていないか確認
						sucess = false;								// 失敗
					}
					if (x > 0 && (location % gridLength == 0)) {	// グリッドから右にはみ出ていないか確認
						sucess = false;								// 失敗
					}
				} else {											// すでに使われているセルだった場合
//					System.out.print(" used " + location);
					sucess = false;									// 失敗
				}
			}
		}

		int x		= 0;	// 最終的な戻り値を得る
		int row		= 0;
		int column	= 0;
		while (x < comSize) {
//			System.out.println("\n");
			grid[coords[x]] = 1;							// セルを「使用済み」にする
			row = (int) (coords[x] / gridLength);			// 横方向の番号を得る
			column = coords[x] % gridLength;				// 縦方向の番号を得る
			temp = String.valueOf(alphabet.charAt(column));	// 番号をアルファベットに変換
			alphaCells.add(temp.concat(Integer.toString(row)));
			x++;
//			System.out.println(" coord " + x + " = " + alphaCells.get(x - 1));
		}
//		System.out.println("\n");

		return alphaCells;
	}
}
