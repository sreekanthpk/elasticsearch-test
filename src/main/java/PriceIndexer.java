import java.net.InetSocketAddress;

import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.node.NodeBuilder;

import com.google.gson.Gson;

public class PriceIndexer {
	
	public static void main(String... x) {
	

		Client client = null;
	    try {

	        client =   TransportClient.builder().build().addTransportAddress(new InetSocketTransportAddress(new InetSocketAddress("192.168.99.100",32770)));
                    
	        Tweet tweet = new Tweet();
	        tweet.setId("11");
	        tweet.setMessage("Hello World");

	        IndexRequest indexRequest = new IndexRequest("twitter","tweet", tweet.getId());
	        indexRequest.source(new Gson().toJson(tweet));
	        IndexResponse response = client.index(indexRequest).actionGet();
	        
	        
	        SearchResponse response1 = client.prepareSearch("twitter")
	                .setTypes("tweet")
	                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
	                .setQuery(QueryBuilders.termQuery("multi", "test"))                 // Query
	                .setPostFilter(QueryBuilders.matchPhraseQuery("id", "1"))     // Filter
	                .setFrom(0).setSize(60).setExplain(true)
	                .execute()
	                .actionGet();
	        
	       System.out.println(response1.toString()); 
	        
	        //recreateIndex(client);
	        //doIndex(client);

	        // ????
	       // searchAll(client); // ?????????????????????????
	        //Thread.sleep(1000 * 1);
	        //searchAll(client); // ????????????

	        //            // ?????
	        //            searchKeyWord(client);
	        //
	        //            // ???????
	        //            searchRange(client);
	        //
	        //            // ??
	        //            searchOrdered(client);
	        //
	        //            // ??
	        //            searchHightlight(client);

	        // ????
	        //searchProps(client);

	    } catch (Exception e) {
	        System.out.println("00000 " + e);
	    } finally {
	        if (client != null) {
	            client.close();
	        }
	    }
	}

}
