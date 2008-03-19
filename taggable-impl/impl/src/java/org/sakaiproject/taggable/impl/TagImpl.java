/**********************************************************************************
 * $URL: https://source.sakaiproject.org/contrib/syracuse/taggable/branches/oncourse_osp_enhancements/taggable-impl/impl/src/java/org/sakaiproject/taggable/impl/TagImpl.java $
 * $Id: TagImpl.java 45892 2008-02-22 19:54:48Z chmaurer@iupui.edu $
 ***********************************************************************************
 *
 * Copyright (c) 2008 The Sakai Foundation.
 *
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 **********************************************************************************/
package org.sakaiproject.taggable.impl;

import java.util.ArrayList;
import java.util.List;

import org.sakaiproject.component.cover.ComponentManager;
import org.sakaiproject.entity.cover.EntityManager;
import org.sakaiproject.entity.api.Entity;
import org.sakaiproject.entity.api.Reference;
import org.sakaiproject.taggable.api.Link;
import org.sakaiproject.taggable.api.Tag;
import org.sakaiproject.taggable.api.TagList;
import org.sakaiproject.site.api.SiteService;
import org.sakaiproject.taggable.api.TagColumn;

public class TagImpl implements Tag
{
	private static final SiteService siteService;

	static {
		siteService = (SiteService) ComponentManager
				.get("org.sakaiproject.site.api.SiteService");
	}

	Link link;

	public TagImpl(Link link) {
		this.link = link;
	}

	public String getActivityRef() {
		return link.getActivityRef();
	}

	public Object getObject() {
		return link.getTagCriteriaRef();
	}

	public String getField(TagColumn column) {
		return getField(column.getName());
	}

	protected String getField(String column) {
		String field;
		Reference ref = EntityManager.newReference(link.getTagCriteriaRef());
		Entity entity = ref.getEntity();
		if (TagList.WORKSITE.equals(column)) {
			field = siteService.getSiteDisplay(ref.getContext());
		} else if (TagList.PARENT.equals(column)) {
			//field = link.getTagCriteria().getParentTitle();
			//field = ">>>>PARENT TITLE SHOULD GO HERE<<<<";
			field = (String)entity.getProperties().get(TagList.PARENT);
		} else if (TagList.CRITERIA.equals(column)) {
			//field = link.getTagCriteria().getTitle();
			//field = ">>>>CRITERIA SHOULD GO HERE<<<<";
			field = (String)entity.getProperties().get(TagList.CRITERIA);
		} else if (TagList.RUBRIC.equals(column)) {
			field = link.getRubric();
		} else if (TagList.RATIONALE.equals(column)) {
			field = link.getRationale();
		} else if (TagList.VISIBLE.equals(column)) {
			field = String.valueOf(link.isVisible());
		} else if (TagList.EXPORTABLE.equals(column)) {
			field = String.valueOf(link.isExportable());
		} else {
			field = TagListImpl.NA;
		}
		return field;
	}

	public List<String> getFields() {
		List<String> fields = new ArrayList<String>();
		fields.add(getField(TagList.WORKSITE));
		fields.add(getField(TagList.PARENT));
		fields.add(getField(TagList.CRITERIA));
		fields.add(getField(TagList.RUBRIC));
		fields.add(getField(TagList.RATIONALE));
		fields.add(getField(TagList.VISIBLE));
		fields.add(getField(TagList.EXPORTABLE));
		return fields;
	}
}
