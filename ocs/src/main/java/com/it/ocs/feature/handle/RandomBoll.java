package com.it.ocs.feature.handle;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;

import com.it.ocs.common.support.IFunction;
import com.it.ocs.common.util.CollectionUtil;

public class RandomBoll {

	private static class Boll {
		private String qihao;
		private String date;
		private Integer[] hotBoll;
		private Integer blueBoll;

		public String getQihao() {
			return qihao;
		}

		public void setQihao(String qihao) {
			this.qihao = qihao;
		}

		public String getDate() {
			return date;
		}

		public void setDate(String date) {
			this.date = date;
		}

		public Integer[] getHotBoll() {
			return hotBoll;
		}

		public void setHotBoll(Integer[] hotBoll) {
			this.hotBoll = hotBoll;
		}

		public Integer getBlueBoll() {
			return blueBoll;
		}

		public void setBlueBoll(Integer blueBoll) {
			this.blueBoll = blueBoll;
		}

		public Boll(Integer[] h, Integer b, String q, String d) {
			this.hotBoll = h;
			this.blueBoll = b;
			this.qihao = q;
			this.date = d;
		}

	}

	private static List<Boll> constructBolls() {
		List<Boll> r = new ArrayList<>();
		try {
			InputStream inputStream = new FileInputStream(new File("D:\\feature\\20160720.xls"));
			HSSFWorkbook wb = new HSSFWorkbook(inputStream);
			HSSFSheet sheet = wb.getSheetAt(0);
			for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
				HSSFRow row = sheet.getRow(i);
				Boll b = new Boll(getHBoll(row), getBBolle(row), String.valueOf(row.getCell(0)),
						String.valueOf(row.getCell(1)));
				r.add(b);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return r;
	}

	private static Integer[] blueNumber(List<Boll> bs) {
		Integer[] bNumber = new Integer[8];
		Map<Integer, Double> m = blueProbability(bs);
		for (Integer n : m.keySet()) {
			System.out.println(n + "  出现的概率是百分之    " + (double) m.get(n) * 100);
		}
		return bNumber;
	}

	private static Integer[] hotNumber(List<Boll> bs) {
		Integer[] hotNumber = new Integer[8];
		Map<Integer, Double> m = hotProbability(bs);
		for (Integer n : m.keySet()) {
			System.out.println(n + "  出现的概率是百分之    " + (double) m.get(n) * 100);
		}
		return hotNumber;
	}

	private static Map<Integer, Double> blueProbability(List<Boll> bs) {
		Map<Integer, Double> r = new HashMap<>();
		for (int i = 1; i <= 16; i++) {
			int count = 0;
			for (Boll b : bs) {
				if (b.getBlueBoll() == i) {
					count++;
				}
			}
			r.put(i, i / (double) bs.size());
		}
		return r;
	}

	private static Map<Integer, Double> hotProbability(List<Boll> bs) {
		Map<Integer, Double> r = new HashMap<>();
		for (int i = 1; i <= 33; i++) {
			int count = 0;
			for (Boll b : bs) {
				if (isExist(b.getHotBoll(), i)) {
					count++;
				}
			}
			r.put(i, i / (double) bs.size());
		}
		return r;
	}

	public static void main(String[] args) {
		List<Boll> bs = constructBolls();
		//
		// if (isRepeat(bs))
		// System.out.println("yes");
		// else
		// System.out.println("no");
		blueNumber(bs);

	}

	private static boolean isRepeat(List<Boll> bs) {
		for (Boll b : bs) {
			Boll sb = CollectionUtil.search(bs, new IFunction<Boll, Boolean>() {
				@Override
				public Boolean excute(Boll obj) {
					if (!b.getBlueBoll().equals(obj.getBlueBoll()) || b.getQihao().equals(obj.getQihao())) {
						return false;
					}
					for (int i = 0; i < b.getHotBoll().length; i++) {
						if (!isExist(obj.getHotBoll(), b.getHotBoll()[i])) {
							return false;
						}
					}
					return true;
				}

			});
			if (null != sb) {
				return true;
			}
		}
		return false;

	}

	private static boolean isExist(Integer[] ss, Integer t) {
		boolean result = false;
		for (int i = 0; i < ss.length; i++) {
			if (ss[i] == t) {
				result = true;
				break;
			}
		}
		return result;
	}

	private static Integer[] getHBoll(HSSFRow row) {
		Integer[] ia = new Integer[6];
		for (int i = 2; i < 8; i++) {
			HSSFCell h = row.getCell(i);
			if (h.getCellType() == Cell.CELL_TYPE_STRING) {
				ia[i - 2] = Integer.parseInt(h.getStringCellValue());
			} else
				ia[i - 2] = (int) h.getNumericCellValue();
		}
		return ia;
	}

	private static Integer getBBolle(HSSFRow row) {
		HSSFCell h = row.getCell(8);
		if (h.getCellType() == Cell.CELL_TYPE_STRING)
			if (h.getStringCellValue().contains(","))
				return Integer.parseInt(h.getStringCellValue().split(",")[0]);
			else
				return Integer.parseInt(h.getStringCellValue());

		else
			return (int) h.getNumericCellValue();
	}
}
