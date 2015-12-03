package plekhotkin.wot.balancer.service;

import java.io.IOException;

import org.json.JSONException;

import plekhotkin.wot.balancer.model.Opponents;

public interface DataService {

	Opponents getOpponents() throws JSONException, IOException;
}
