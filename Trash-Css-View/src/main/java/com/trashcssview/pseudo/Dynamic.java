package com.trashcssview.pseudo;

public class Dynamic {
	public static class CLASSES {
		public static final String[] ARRAY_OF_CLASSES = { 
			"link", "visited", "hover", "active", "focus"
		};
		
		// "link", "visited" only for a;

		public static final String REG_EXP;

		static {
			StringBuilder sb = new StringBuilder("");
			sb.append(":(");
			sb.append(ARRAY_OF_CLASSES[0]);
			for (int i = 1; i < ARRAY_OF_CLASSES.length; i++) {

				sb.append("|");
				sb.append(ARRAY_OF_CLASSES[i]);
			}
			sb.append(")");

			REG_EXP = sb.toString();
		}
	}
}
