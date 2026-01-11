package com.healthcare.dto;


import lombok.Data;
import java.util.List;

@Data
public class ThingSpeakResponseDto {

    private ChannelDto channel;
    private List<ThingSpeakFeedDto> feeds;
	public ChannelDto getChannel() {
		return channel;
	}
	public void setChannel(ChannelDto channel) {
		this.channel = channel;
	}
	public List<ThingSpeakFeedDto> getFeeds() {
		return feeds;
	}
	public void setFeeds(List<ThingSpeakFeedDto> feeds) {
		this.feeds = feeds;
	}
}
