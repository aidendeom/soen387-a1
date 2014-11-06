package org.soen387.test.ser;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.custommonkey.xmlunit.XMLAssert;
import org.custommonkey.xmlunit.XMLUnit;
import org.custommonkey.xmlunit.exceptions.XpathException;
import org.junit.Test;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class TestCheckers {
	CloseableHttpClient httpclient = HttpClients.createDefault();
	public final String BASE_URL = FieldMap.current.get().get("BASE_URL");
	XPath xPath =  XPathFactory.newInstance().newXPath();
	
	@Test
	public void testLogIn() throws SAXException, IOException, XpathException, XPathExpressionException {
		Document d = login("fred", "fred");
		
		//Should fail, since I haven't registered them yet
		assertFailure(d);
		
		register("fred", "fred", "fred", "fredson", "fred@fred.com");
		
		//I have now registered this user, so it should pass
		d = login("fred", "fred");
		assertSuccess(d);
	}
	
	@Test
	public void testLogOut() throws SAXException, IOException, XpathException, XPathExpressionException {
		login("fred", "fred");
		//A full test might then try to challenge a user that one has not challenged to confirmed that one is not allowed
		//to do so when not logged in.
		Document d = logout();
		assertSuccess(d);
	}
	
	@Test
	public void testRegister() throws SAXException, IOException, XpathException, XPathExpressionException {
		Document d = register("bob", "bob", "bob", "bobson", "bob@fred.com");
		long id = getPlayerId(d);
		XMLAssert.assertXpathExists("/checkers/player[@firstName='bob']", d);
		XMLAssert.assertXpathExists("/checkers/player[@lastName='bobson']", d);
		XMLAssert.assertXpathExists("/checkers/player[@email='bob@fred.com']", d);
		XMLAssert.assertXpathExists("/checkers/player/user[@username='bob']", d);
		d = login("bob", "bob");
		assertSuccess(d);
		d = listPlayers();
		XMLAssert.assertXpathExists("/checkers/players/player[@id='" + id + "']", d);
	}
	
	@Test
	public void testListUsers() throws SAXException, IOException, XpathException, XPathExpressionException {
		register("alice", "alice", "alice", "aliceson", "alice@fred.com");
		register("dora", "dora", "dora", "dorason", "dora@fred.com");
		Document d = listPlayers();
		XMLAssert.assertXpathExists("/checkers/players/player[@firstName='alice']", d);
		XMLAssert.assertXpathExists("/checkers/players/player[@firstName='dora']", d);
		assertSuccess(d);
	}


	@Test
	public void testViewUserStats() throws SAXException, IOException, XpathException, XPathExpressionException {
		Document d = register("elsa", "elsa", "elsa", "elsason", "elsa@fred.com");
		long elsaId = getPlayerId(d);
		register("george", "george", "george", "georgeson", "george@fred.com");
		login("george", "george");
		
		d = challengePlayer(elsaId);
		long challengeId = getChallengeId(d);
		int challengeVersion = getChallengeVersion(d);
		
		login("elsa", "elsa");
		respondToChallenge(challengeId, challengeVersion, true);

		d = viewPlayerStats(elsaId);
		
		XMLAssert.assertXpathExists("/checkers/player[@firstName='elsa']/games/game/firstPlayer[@refid='" + elsaId + "'] ", d);
		
		
		assertSuccess(d);
	}
	
	@Test
	public void testChallengeUser() throws SAXException, IOException, XpathException, XPathExpressionException {
		Document d = register("hal", "hal", "hal", "halson", "hal@fred.com");
		long halId = getPlayerId(d);
		register("igor", "igor", "igor", "igorson", "igor@fred.com");
		login("igor", "igor");
		
		d = challengePlayer(halId);
		long challengeId = getChallengeId(d);

		d = listChallenges();
		
		XMLAssert.assertXpathExists("/checkers/challenges/challenge[@id='" + challengeId + "'] ", d);
		
		
		assertSuccess(d);
	}
	
	@Test
	public void testRespondToChallenge() throws SAXException, IOException, XpathException, XPathExpressionException {
		Document d = register("jesse", "jesse", "jesse", "jesseson", "jesse@fred.com");
		long jesseId = getPlayerId(d);
		register("kathryn", "kathryn", "kathryn", "kathrynson", "kathryn@fred.com");
		login("kathryn", "kathryn");
		
		d = challengePlayer(jesseId);
		long challengeId = getChallengeId(d);
		int challengeVersion = getChallengeVersion(d);
		
		login("jesse", "jesse");
		respondToChallenge(challengeId, challengeVersion, false);

		d = viewPlayerStats(jesseId);
		
		XMLAssert.assertXpathNotExists("/checkers/player[@firstName='jesse']/games/game", d);
		
		d = listChallenges();
		
		XMLAssert.assertXpathExists("/checkers/challenges/challenge[@id='" + challengeId + "' and @status='2'] ", d);
		
		assertSuccess(d);
	}
	
	@Test
	public void testListGames() throws SAXException, IOException, XpathException, XPathExpressionException {
		Document d = register("lisa", "lisa", "lisa", "lisason", "lisa@fred.com");
		long lisaId = getPlayerId(d);

		d = register("mason", "mason", "mason", "masonson", "mason@fred.com");
		long masonId = getPlayerId(d);
		
		d = register("nancy", "nancy", "nancy", "nancyson", "nancy@fred.com");
		long nancyId = getPlayerId(d);
		
		login("nancy", "nancy");
		
		d = challengePlayer(lisaId);
		long challengeId1 = getChallengeId(d);
		int challengeVersion1 = getChallengeVersion(d);
		
		d = challengePlayer(masonId);
		long challengeId2 = getChallengeId(d);
		int challengeVersion2 = getChallengeVersion(d);
		
		login("lisa", "lisa");
		respondToChallenge(challengeId1, challengeVersion1, true);
		
		login("mason", "mason");
		respondToChallenge(challengeId2, challengeVersion2, true);

		logout();
		
		d = listGames();
		
		XMLAssert.assertXpathExists("/checkers/games/game/firstPlayer[@refid='" + lisaId + "'] ", d);
		XMLAssert.assertXpathExists("/checkers/games/game/firstPlayer[@refid='" + masonId + "'] ", d);
		XMLAssert.assertXpathExists("/checkers/games/game/secondPlayer[@refid='" + nancyId + "'] ", d);
		XMLAssert.assertXpathNotExists("/checkers/games/game/firstPlayer[@refid='" + nancyId + "'] ", d);
		
		System.out.println("what");
		assertSuccess(d);
	}
	
	@Test
	public void testViewGame() throws SAXException, IOException, XpathException, XPathExpressionException {
		Document d = register("ollie", "ollie", "ollie", "ollieson", "ollie@fred.com");
		long ollieId = getPlayerId(d);
		
		d = register("percy", "percy", "percy", "percyson", "percy@fred.com");
		long percyId = getPlayerId(d);
		
		login("percy", "percy");
		
		d = challengePlayer(ollieId);
		long challengeId = getChallengeId(d);
		int challengeVersion = getChallengeVersion(d);
		
		login("ollie", "ollie");
		respondToChallenge(challengeId, challengeVersion, true);

		d = listGames();
		
		XMLAssert.assertXpathExists("/checkers/games/game/firstPlayer[@refid='" + ollieId + "']/../secondPlayer[@refid='" + percyId + "'] ", d);
		long gameId = getGameId(d, ollieId, percyId);
		
		d = viewGame(gameId);
		XMLAssert.assertXpathExists("/checkers/game[@id='" + gameId + "']/firstPlayer[@refid='" + ollieId + "'] ", d);
		XMLAssert.assertXpathExists("/checkers/game[@id='" + gameId + "']/currentPlayer[@refid='" + ollieId + "'] ", d);
		XMLAssert.assertXpathExists("/checkers/game[@id='" + gameId + "']/secondPlayer[@refid='" + percyId + "'] ", d);
		XMLAssert.assertXpathExists("/checkers/game[@id='" + gameId + "']/pieces[.=\"b b b b  b b b bb b b b                  r r r rr r r r  r r r r\"]", d);

		assertSuccess(d);
	}
	
	@Test
	public void testViewChallenges() throws SAXException, IOException, XpathException, XPathExpressionException {
		Document d = register("q", "q", "q", "qson", "q@fred.com");
		long qId = getPlayerId(d);
		d = register("ryan", "ryan", "ryan", "ryanson", "ryan@fred.com");
		long ryanId = getPlayerId(d);
		login("ryan", "ryan");
		d = listChallenges();
		XMLAssert.assertXpathNotExists("/checkers/challenges/challenge/challenger[@refid='" + ryanId + "'] ", d);
		XMLAssert.assertXpathNotExists("/checkers/challenges/challenge/challengee[@refid='" + ryanId + "'] ", d);
		
		d = challengePlayer(qId);

		d = listChallenges();
		
		XMLAssert.assertXpathExists("/checkers/challenges/challenge/challenger[@refid='" + ryanId + "'] ", d);
		XMLAssert.assertXpathNotExists("/checkers/challenges/challenge/challengee[@refid='" + ryanId + "'] ", d);
		
		assertSuccess(d);
	}
	
	public final String LOGIN = BASE_URL+FieldMap.current.get().get("LOGIN_PATH");
	public Document login(String user, String pass) throws ParseException, ClientProtocolException, IOException, SAXException {
		
		HttpPost httpPost = new HttpPost(LOGIN);
		List <NameValuePair> nvps = new ArrayList <NameValuePair>();
		nvps.add(new BasicNameValuePair(FieldMap.current.get().get("XML_PARAM"), FieldMap.current.get().get("XML_VALUE")));
		nvps.add(new BasicNameValuePair(FieldMap.current.get().get("USERNAME_PARAM"), user));
		nvps.add(new BasicNameValuePair(FieldMap.current.get().get("PASSWORD_PARAM"), pass));
		httpPost.setEntity(new UrlEncodedFormEntity(nvps));
		CloseableHttpResponse requestResponse = httpclient.execute(httpPost);
		String response = EntityUtils.toString(requestResponse.getEntity());
		String details = prettyPrintPost(httpPost, nvps, response);
		System.out.println(details);
		requestResponse.close();
		return XMLUnit.buildControlDocument(response);
	}

	public final String LOGOUT = BASE_URL+FieldMap.current.get().get("LOGOUT_PATH");
	public Document logout() throws ParseException, ClientProtocolException, IOException, SAXException {
		
		HttpPost httpPost = new HttpPost(LOGOUT);
		List <NameValuePair> nvps = new ArrayList <NameValuePair>();
		nvps.add(new BasicNameValuePair(FieldMap.current.get().get("XML_PARAM"), FieldMap.current.get().get("XML_VALUE")));
		httpPost.setEntity(new UrlEncodedFormEntity(nvps));
		CloseableHttpResponse requestResponse = httpclient.execute(httpPost);
		String response = EntityUtils.toString(requestResponse.getEntity());
		String details = prettyPrintPost(httpPost, nvps, response);
		System.out.println(details);
		requestResponse.close();
		return XMLUnit.buildControlDocument(response);
	}
	
	public final String REGISTER = BASE_URL+FieldMap.current.get().get("REGISTER_PATH");
	public Document register(String user, String pass, String first, String last, String email) throws ParseException, ClientProtocolException, IOException, SAXException {
		
		HttpPost httpPost = new HttpPost(REGISTER);
		List <NameValuePair> nvps = new ArrayList <NameValuePair>();
		nvps.add(new BasicNameValuePair(FieldMap.current.get().get("XML_PARAM"), FieldMap.current.get().get("XML_VALUE")));
		nvps.add(new BasicNameValuePair(FieldMap.current.get().get("USERNAME_PARAM"), user));
		nvps.add(new BasicNameValuePair(FieldMap.current.get().get("PASSWORD_PARAM"), pass));
		nvps.add(new BasicNameValuePair(FieldMap.current.get().get("FIRSTNAME_PARAM"), first));
		nvps.add(new BasicNameValuePair(FieldMap.current.get().get("LASTNAME_PARAM"), last));
		nvps.add(new BasicNameValuePair(FieldMap.current.get().get("EMAIL_PARAM"), email));
		httpPost.setEntity(new UrlEncodedFormEntity(nvps));
		CloseableHttpResponse requestResponse = httpclient.execute(httpPost);
		String response = EntityUtils.toString(requestResponse.getEntity());
		String details = prettyPrintPost(httpPost, nvps, response);
		System.out.println(details);
		requestResponse.close();
		return XMLUnit.buildControlDocument(response);
	}
	
	public final String LIST_PLAYERS = BASE_URL+FieldMap.current.get().get("LIST_PLAYERS_PATH");
	public Document listPlayers() throws ParseException, ClientProtocolException, IOException, SAXException {
		
		HttpPost httpPost = new HttpPost(LIST_PLAYERS);
		List <NameValuePair> nvps = new ArrayList <NameValuePair>();
		nvps.add(new BasicNameValuePair(FieldMap.current.get().get("XML_PARAM"), FieldMap.current.get().get("XML_VALUE")));
		httpPost.setEntity(new UrlEncodedFormEntity(nvps));
		CloseableHttpResponse requestResponse = httpclient.execute(httpPost);
		String response = EntityUtils.toString(requestResponse.getEntity());
		String details = prettyPrintPost(httpPost, nvps, response);
		System.out.println(details);
		requestResponse.close();
		return XMLUnit.buildControlDocument(response);
	}
	
	public final String VIEW_PLAYER_STATS = BASE_URL+FieldMap.current.get().get("VIEW_PLAYER_STATS_PATH");
	public Document viewPlayerStats(long id) throws ParseException, ClientProtocolException, IOException, SAXException {
		
		HttpPost httpPost = new HttpPost(VIEW_PLAYER_STATS);
		List <NameValuePair> nvps = new ArrayList <NameValuePair>();
		nvps.add(new BasicNameValuePair(FieldMap.current.get().get("XML_PARAM"), FieldMap.current.get().get("XML_VALUE")));
		nvps.add(new BasicNameValuePair(FieldMap.current.get().get("PLAYER_PARAM"), ""+id));
		httpPost.setEntity(new UrlEncodedFormEntity(nvps));
		CloseableHttpResponse requestResponse = httpclient.execute(httpPost);
		String response = EntityUtils.toString(requestResponse.getEntity());
		String details = prettyPrintPost(httpPost, nvps, response);
		System.out.println(details);
		requestResponse.close();
		return XMLUnit.buildControlDocument(response);
	}
	
	public final String CHALLENGE_PLAYER = BASE_URL+FieldMap.current.get().get("CHALLENGE_PLAYER_PATH");
	public Document challengePlayer(long id) throws ParseException, ClientProtocolException, IOException, SAXException {
		
		HttpPost httpPost = new HttpPost(CHALLENGE_PLAYER);
		List <NameValuePair> nvps = new ArrayList <NameValuePair>();
		nvps.add(new BasicNameValuePair(FieldMap.current.get().get("XML_PARAM"), FieldMap.current.get().get("XML_VALUE")));
		nvps.add(new BasicNameValuePair(FieldMap.current.get().get("TARGET_PLAYER_PARAM"), ""+id));
		httpPost.setEntity(new UrlEncodedFormEntity(nvps));
		CloseableHttpResponse requestResponse = httpclient.execute(httpPost);
		String response = EntityUtils.toString(requestResponse.getEntity());
		String details = prettyPrintPost(httpPost, nvps, response);
		System.out.println(details);
		requestResponse.close();
		return XMLUnit.buildControlDocument(response);
	}
	
	public final String RESPOND_TO_CHALLENGE = BASE_URL+FieldMap.current.get().get("RESPOND_TO_CHALLNGE_PATH");
	public Document respondToChallenge(long challenge, int version, boolean accept) throws ParseException, ClientProtocolException, IOException, SAXException {
		
		HttpPost httpPost = new HttpPost(RESPOND_TO_CHALLENGE);
		List <NameValuePair> nvps = new ArrayList <NameValuePair>();
		nvps.add(new BasicNameValuePair(FieldMap.current.get().get("XML_PARAM"), FieldMap.current.get().get("XML_VALUE")));
		nvps.add(new BasicNameValuePair(FieldMap.current.get().get("CHALLENGE_PARAM"), ""+challenge));
		nvps.add(new BasicNameValuePair(FieldMap.current.get().get("CHALLENGE_VERSION_PARAM"), ""+version));
		nvps.add(new BasicNameValuePair(FieldMap.current.get().get("CHALLENGE_RESPONSE_PARAM"), accept?FieldMap.current.get().get("CHALLENGE_ACCEPT_VALUE"):FieldMap.current.get().get("CHALLENGE_REFUSE_VALUE")));
		httpPost.setEntity(new UrlEncodedFormEntity(nvps));
		CloseableHttpResponse requestResponse = httpclient.execute(httpPost);
		String response = EntityUtils.toString(requestResponse.getEntity());
		String details = prettyPrintPost(httpPost, nvps, response);
		System.out.println(details);
		requestResponse.close();
		return XMLUnit.buildControlDocument(response);
	}	
	
	public final String LIST_GAMES = BASE_URL+FieldMap.current.get().get("LIST_GAMES_PATH");
	public Document listGames() throws ParseException, ClientProtocolException, IOException, SAXException {
		
		HttpPost httpPost = new HttpPost(LIST_GAMES);
		List <NameValuePair> nvps = new ArrayList <NameValuePair>();
		nvps.add(new BasicNameValuePair(FieldMap.current.get().get("XML_PARAM"), FieldMap.current.get().get("XML_VALUE")));
		httpPost.setEntity(new UrlEncodedFormEntity(nvps));
		CloseableHttpResponse requestResponse = httpclient.execute(httpPost);
		String response = EntityUtils.toString(requestResponse.getEntity());
		String details = prettyPrintPost(httpPost, nvps, response);
		System.out.println(details);
		requestResponse.close();
		return XMLUnit.buildControlDocument(response);
	}
	
	public final String VIEW_GAME = BASE_URL+FieldMap.current.get().get("VIEW_GAME_PATH");
	public Document viewGame(long game) throws ParseException, ClientProtocolException, IOException, SAXException {
		
		HttpPost httpPost = new HttpPost(VIEW_GAME);
		List <NameValuePair> nvps = new ArrayList <NameValuePair>();
		nvps.add(new BasicNameValuePair(FieldMap.current.get().get("XML_PARAM"), FieldMap.current.get().get("XML_VALUE")));
		nvps.add(new BasicNameValuePair(FieldMap.current.get().get("GAME_PARAM"), ""+game));
		httpPost.setEntity(new UrlEncodedFormEntity(nvps));
		CloseableHttpResponse requestResponse = httpclient.execute(httpPost);
		String response = EntityUtils.toString(requestResponse.getEntity());
		String details = prettyPrintPost(httpPost, nvps, response);
		System.out.println(details);
		requestResponse.close();
		return XMLUnit.buildControlDocument(response);
	}
	
	public final String LIST_CHALLENGES = BASE_URL+FieldMap.current.get().get("LIST_CHALLENGES_PATH");
	public Document listChallenges() throws ParseException, ClientProtocolException, IOException, SAXException {
		
		HttpPost httpPost = new HttpPost(LIST_CHALLENGES);
		List <NameValuePair> nvps = new ArrayList <NameValuePair>();
		nvps.add(new BasicNameValuePair(FieldMap.current.get().get("XML_PARAM"), FieldMap.current.get().get("XML_VALUE")));
		httpPost.setEntity(new UrlEncodedFormEntity(nvps));
		CloseableHttpResponse requestResponse = httpclient.execute(httpPost);
		String response = EntityUtils.toString(requestResponse.getEntity());
		String details = prettyPrintPost(httpPost, nvps, response);
		System.out.println(details);
		requestResponse.close();
		return XMLUnit.buildControlDocument(response);
	}
	
	private String prettyPrintPost(HttpPost httpPost, List<NameValuePair> nvps,
			String response) {
		StringBuilder sb = new StringBuilder();
		sb.append("******Request"+"\n");
		sb.append(httpPost+"\n");
		for(NameValuePair nvp: nvps) {
			sb.append("\t" + nvp.toString()+"\n");
		}
		
		sb.append("******Response"+"\n");
		sb.append(response);
		return  FieldMap.current.get().get("DEBUG").equals("true")?sb.toString():"";
	}

	private long getPlayerId(Document d) throws NumberFormatException, XPathExpressionException {
		return Long.parseLong((String) xPath.evaluate("/checkers/player/@id", d, XPathConstants.STRING));
	}
	
	private long getGameId(Document d, long firstPlayer, long secondPlayer) throws NumberFormatException, XPathExpressionException {
		return Long.parseLong((String) xPath.evaluate("/checkers/games/game/firstPlayer[@refid='" + firstPlayer + "']/../secondPlayer[@refid='" + secondPlayer + "']/../@id", d, XPathConstants.STRING));
	}
	
	private long getChallengeId(Document d) throws NumberFormatException, XPathExpressionException {
		return Long.parseLong((String) xPath.evaluate("/checkers/challenge/@id", d, XPathConstants.STRING));
	}
	
	private int getChallengeVersion(Document d) throws NumberFormatException, XPathExpressionException {
		return Integer.parseInt((String) xPath.evaluate("/checkers/challenge/@version", d, XPathConstants.STRING));
	}
	
	public void assertSuccess(Document d) throws XpathException, XPathExpressionException {
		XMLAssert.assertXpathExists("/checkers/status[.=\"success\"]", d);
	}
	
	public void assertFailure(Document d) throws XpathException, XPathExpressionException {
		XMLAssert.assertXpathNotExists("/checkers/status[.=\"success\"]", d);
	}

	
}


