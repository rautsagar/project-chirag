package com.project.chirag.youtube.parser;

import java.util.List;

import com.project.chirag.youtube.domain.Video;

public interface AtomParser {

	List<Video> getVideos();

	public class ParseException extends Exception {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public ParseException(Exception origin) {
			super(origin);
		}
	}
}