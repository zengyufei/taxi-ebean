package com.zzsim.taxi.admin.enumerated.file;

public enum UploadFileType {

	image(1,"图片","BMP,JPG,JPEG,PNG,GIF,SVG,TIFF"),
	video(2,"视频","OGG,MPEG4,WEBM"),
    audio(3,"音频","MP3,WMA,WAV,RAM"),
    docs(4,"文档","TXT,DOC,DOCX,XLS,XLSX"),
    compress(5,"压缩包","ZIP,RAR,GZ"),
	;
	
	private int index;
	private String description;
	private String suffix ;
	
	UploadFileType(int index, String desc, String suffix){
		this.index = index;
		this.description = desc;
		this.suffix = suffix;
	}
	
	public String getDescription() {
		return description;
	}

	public int getIndex() {
		return index;
	}
	public String getSuffix(){
		return suffix;
	}
}
