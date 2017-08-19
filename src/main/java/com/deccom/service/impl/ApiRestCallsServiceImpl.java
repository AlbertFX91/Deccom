package com.deccom.service.impl;

import com.deccom.service.ApiRestCallsService;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Version;
import com.restfb.types.User;

public class ApiRestCallsServiceImpl implements ApiRestCallsService {

	public ApiRestCallsServiceImpl() {
	}

	public void printData() {
		String accessToken = "EAACEdEose0cBACSZA4cqDVEpbnn6jn0Xr74Q2DENSxspUEJ3eLw2ZCssSRT4rSLzB5MExHwjPRQK9ZArnHQQgvWu6RHn5RyY7SF8e1taHZCXZCiMWhvCypGOOFTt6FbHqoUOFuyBGtRYUzlyHpMaYdCQR6qcIx7nWM2w5oX6NH4V8bzNtwEwtLO5OZA6lTEJxqrUHOzgiYxZBZAT6wmU9bY8BG4tbx4X3FEZD";
		FacebookClient facebookClient = new DefaultFacebookClient(accessToken,
				Version.LATEST);
		User me = facebookClient.fetchObject("me", User.class);

		System.out.println(me.getName());
		System.out.println(me.getId());
	}

}
