package plekhotkin.wot.balancer;

import java.io.IOException;

import org.json.JSONException;

import plekhotkin.wot.balancer.model.Opponents;
import plekhotkin.wot.balancer.service.DataService;
import plekhotkin.wot.balancer.service.impl.DataServiceImpl;

public final class Application {

	private DataService dataService;

	public Application(final String id, final String apiEndpoint) {
		dataService = new DataServiceImpl(id, apiEndpoint);
	}

	public void printBattleInfo() throws JSONException, IOException {
		System.out.println("Processing...");
		Opponents opponents = dataService.getOpponents();
		Balancer.balance(opponents);

		opponents.clanA.printStats();
		opponents.clanB.printStats();
	}

	public static void main(final String[] args) throws IOException,
			JSONException {
		String apiEndpoint = "http://api.worldoftanks.ru/wot/";
		String applicationId = "demo";
		if (args.length == 2) {
			apiEndpoint = args[0];
			applicationId = args[1];
		}
		Application application = new Application(applicationId, apiEndpoint);
		application.printBattleInfo();

	}

	void validateParameters(final String[] args) {
		boolean valid = true;

		// TODO: real validation here

		if (!valid) {
			// TODO: investigate what should be used for output messages
			// Note: possible alternatives: logger.
			System.out.println("Some of the parameters are invalid.");
			System.exit(0);
		}

	}

}
