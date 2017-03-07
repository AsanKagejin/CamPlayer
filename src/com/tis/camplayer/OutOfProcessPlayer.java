package com.tis.camplayer;

import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.binding.LibVlcFactory;
import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Asan on 03.03.2017.
 */

class OutOfProcessPlayer {

	private OutOfProcessPlayer(final long canvasID){
		LibVlc libInstance = LibVlcFactory.factory().synchronise().create();
		MediaPlayerFactory factory = new MediaPlayerFactory(libInstance, "--no-video-title");
		EmbeddedMediaPlayer player = factory.newEmbeddedMediaPlayer();
		factory.newVideoSurface(canvasID).attach(libInstance, player);
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		String inputLine = "";
		while (true){
			try {
				inputLine = input.readLine();
			} catch (IOException e) { e.printStackTrace(); }
			if (inputLine.startsWith("play ")){
				inputLine = inputLine.substring("play ".length());
				player.playMedia(inputLine);
			} else if (inputLine.equals("close")) {
				player.release();
				factory.release();
				System.exit(0);
			}
		}
	}

	public static void main(String args[]){
		new OutOfProcessPlayer(Long.parseLong(args[0]));
		System.out.print("OutOfProcessPlayer has started");
	}
}
