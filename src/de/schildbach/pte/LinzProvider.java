/*
 * Copyright 2010 the original author or authors.
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package de.schildbach.pte;

import java.io.IOException;
import java.util.Date;

import de.schildbach.pte.dto.GetConnectionDetailsResult;
import de.schildbach.pte.dto.QueryConnectionsResult;
import de.schildbach.pte.dto.StationLocationResult;
import de.schildbach.pte.util.ParserUtils;

/**
 * @author Andreas Schildbach
 */
public class LinzProvider extends AbstractEfaProvider
{
	public static final String NETWORK_ID = "www.linzag.at";
	public static final String API_BASE = "http://www.linzag.at/linz/";

	public boolean hasCapabilities(final Capability... capabilities)
	{
		for (final Capability capability : capabilities)
			if (capability == Capability.DEPARTURES)
				return true;

		return false;
	}

	private static final String AUTOCOMPLETE_URI = API_BASE + "XML_STOPFINDER_REQUEST"
			+ "?outputFormat=XML&coordOutputFormat=WGS84&name_sf=%s&type_sf=%s";
	private static final String AUTOCOMPLETE_TYPE = "any"; // any, stop, street, poi
	private static final String ENCODING = "ISO-8859-1";

	@Override
	protected String autocompleteUri(final CharSequence constraint)
	{
		return String.format(AUTOCOMPLETE_URI, ParserUtils.urlEncode(constraint.toString(), ENCODING), AUTOCOMPLETE_TYPE);
	}

	private static final String NEARBY_LATLON_URI = API_BASE
			+ "XSLT_DM_REQUEST"
			+ "?outputFormat=XML&mode=direct&coordOutputFormat=WGS84&mergeDep=1&useAllStops=1&name_dm=%2.6f:%2.6f:WGS84&type_dm=coord&itOptionsActive=1&ptOptionsActive=1&useProxFootSearch=1&excludedMeans=checkbox";

	@Override
	protected String nearbyLatLonUri(final int lat, final int lon)
	{
		return String.format(NEARBY_LATLON_URI, latLonToDouble(lon), latLonToDouble(lat));
	}

	private static final String NEARBY_STATION_URI = API_BASE
			+ "XSLT_DM_REQUEST"
			+ "?outputFormat=XML&mode=direct&coordOutputFormat=WGS84&mergeDep=1&useAllStops=1&name_dm=%s&type_dm=stop&itOptionsActive=1&ptOptionsActive=1&useProxFootSearch=1&excludedMeans=checkbox";

	@Override
	protected String nearbyStationUri(final String stationId)
	{
		return String.format(NEARBY_STATION_URI, stationId);
	}

	public StationLocationResult stationLocation(final String stationId) throws IOException
	{
		throw new UnsupportedOperationException();
	}

	public QueryConnectionsResult queryConnections(final LocationType fromType, final String from, final LocationType viaType, final String via,
			final LocationType toType, final String to, final Date date, final boolean dep, final WalkSpeed walkSpeed) throws IOException
	{
		throw new UnsupportedOperationException();
	}

	public QueryConnectionsResult queryMoreConnections(final String uri) throws IOException
	{
		throw new UnsupportedOperationException();
	}

	public GetConnectionDetailsResult getConnectionDetails(final String connectionUri) throws IOException
	{
		throw new UnsupportedOperationException();
	}

	public String departuresQueryUri(final String stationId, final int maxDepartures)
	{
		final StringBuilder uri = new StringBuilder();
		uri.append(API_BASE).append("XSLT_DM_REQUEST");
		uri.append("?outputFormat=XML");
		uri.append("&coordOutputFormat=WGS84");
		uri.append("&type_dm=stop");
		uri.append("&name_dm=").append(stationId);
		uri.append("&mode=direct");
		return uri.toString();
	}
}
