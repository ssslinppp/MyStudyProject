package com.sssppp.Lucene.TextProcess.QueryAndFilterData.Filters.CustomFilter;

import java.io.IOException;

import org.apache.lucene.index.AtomicReaderContext;
import org.apache.lucene.index.SortedDocValues;
import org.apache.lucene.search.DocIdSet;
import org.apache.lucene.search.FieldCache;
import org.apache.lucene.search.FieldCacheDocIdSet;
import org.apache.lucene.search.Filter;
import org.apache.lucene.util.Bits;
import org.apache.lucene.util.BytesRef;

public class UserSecurityFilter extends Filter {
	private String userIdField;
	private String groupIdField;
	private String userId;
	private String groupId;

	public UserSecurityFilter(String userIdField, String groupIdField,
			String userId, String groupId) {
		this.userIdField = userIdField;
		this.groupIdField = groupIdField;
		this.userId = userId;
		this.groupId = groupId;
	}

	@Override
	public DocIdSet getDocIdSet(AtomicReaderContext context, Bits acceptDocs)
			throws IOException {
		final SortedDocValues userIdDocValues = FieldCache.DEFAULT
				.getTermsIndex(context.reader(), userIdField);
		final SortedDocValues groupIdDocValues = FieldCache.DEFAULT
				.getTermsIndex(context.reader(), groupIdField);

		//返回Field的ordinal
		final int userIdOrd = userIdDocValues.lookupTerm(new BytesRef(userId));
		final int groupIdOrd = groupIdDocValues
				.lookupTerm(new BytesRef(groupId));

		return new FieldCacheDocIdSet(context.reader().maxDoc(), acceptDocs) {
			@Override
			protected final boolean matchDoc(int doc) {
				//返回指定docId的ordinal
				final int userIdDocOrd = userIdDocValues.getOrd(doc);
				final int groupIdDocOrd = groupIdDocValues.getOrd(doc);
				return userIdDocOrd == userIdOrd || groupIdDocOrd >= groupIdOrd;
			}
		};
	}

}

















