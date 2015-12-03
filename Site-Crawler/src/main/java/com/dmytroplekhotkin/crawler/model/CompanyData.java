package com.dmytroplekhotkin.crawler.model;

import java.util.List;

public final class CompanyData {

	private final String country;
	private final String city;
	private final List<String> phoneNumberList;
	private final String description;

	public CompanyData(String country, List<String> phoneNumberList,
			String address, String site, String title) {
		this.country = country;
		this.city = address.split(",")[0];
		this.phoneNumberList = phoneNumberList;
		String[] addressParts = address.split(",");
		StringBuilder descBuilder = new StringBuilder();
		descBuilder.append(title);
		if (addressParts.length == 3) {
			descBuilder.append(", полный адресс: ");
			descBuilder.append(address);
		}
		if (phoneNumberList.size() > 1) {
			descBuilder.append(", доп. тел.: ");
			for (int i = 1; i < phoneNumberList.size(); i++) {
				if (i != 1) {
					descBuilder.append(",");
				}
				descBuilder.append(phoneNumberList.get(i));
			}
		}
		if (site != null && !site.isEmpty()) {
			descBuilder.append(", сайт: ");
			descBuilder.append(site);
		}
		this.description = descBuilder.toString();
	}

	public String getCountry() {
		return country;
	}

	public String getCity() {
		return city;
	}

	public List<String> getPhoneNumber() {
		return phoneNumberList;
	}

	public String getDescription() {
		return description;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;

		result = prime * result + ((city == null) ? 0 : city.hashCode());
		result = prime * result + ((country == null) ? 0 : country.hashCode());
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result
				+ ((phoneNumberList == null) ? 0 : phoneNumberList.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		CompanyData other = (CompanyData) obj;

		if (city == null) {
			if (other.city != null) {
				return false;
			}
		} else if (!city.equals(other.city)) {
			return false;
		}
		if (country == null) {
			if (other.country != null) {
				return false;
			}
		} else if (!country.equals(other.country)) {
			return false;
		}
		if (description == null) {
			if (other.description != null) {
				return false;
			}
		} else if (!description.equals(other.description)) {
			return false;
		}
		if (phoneNumberList == null) {
			if (other.phoneNumberList != null) {
				return false;
			}
		} else if (!phoneNumberList.equals(other.phoneNumberList)) {
			return false;
		}

		return true;
	}

	@Override
	public String toString() {
		return String.format("%s|%s|%s|%s", country, city, phoneNumberList.get(0),
				description);
	}
}
