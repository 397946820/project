package com.it.ocs.synchronou.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value="/UploadSiteHostedPictures")
public class UploadSiteHostedPicturesController {

	@RequestMapping(value="/show")
	public String show(){
		
		return "admin/ebaySynchronous/uploadSiteHostedPictures";
		
	}
}
