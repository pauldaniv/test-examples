package com.pauldaniv.spark.services.impl;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.apache.spark.sql.functions.collect_set;
import static org.apache.spark.sql.functions.column;
import static scala.collection.JavaConversions.asScalaBuffer;

import com.pauldaniv.spark.model.Engagement;
import com.pauldaniv.spark.model.User;
import com.pauldaniv.spark.services.ResourceResolver;
import com.pauldaniv.spark.services.DataFrameExample;
import lombok.RequiredArgsConstructor;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.sql.Column;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SQLContext;
import org.springframework.stereotype.Service;
import scala.collection.JavaConversions;
import scala.collection.mutable.Seq;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class DataFrameExampleImpl implements DataFrameExample {

    public static final String GUID = "guid";
    public static final String AUDIT_UNITS = "auditUnits";
    public static final String AUDIT_UNIT = "auditUnit";
    public static final String LEADERS_GUI_DS_RAW = "leadersGUIDsRaw";
    public static final String LEADERS_EMAILS_RAW = "leadersEmailsRaw";
    public static final String LEADERS_FULL_NAMES_RAW = "leadersFullNamesRaw";
    public static final String MANAGERS_GUI_DS_RAW = "managersGUIDsRaw";
    public static final String MANAGERS_EMAILS_RAW = "managersEmailsRaw";
    public static final String MANAGERS_FULL_NAMES_RAW = "managersFullNamesRaw";
    public static final String MEMBERS_GUIDS_RAW = "membersGUIDsRaw";
    public static final String MEMBERS_EMAILS_RAW = "membersEmailsRaw";
    public static final String MEMBERS_FULL_NAMES_RAW = "membersFullNamesRaw";
    public static final String NAME = "name";
    public static final String COLUMN_ENGAGEMENT_ID = "EngagementID";
    public static final String COLUMN_ENGAGEMENT_NAME = "Engagement Name";
    public static final String COLUMN_AUDIT_UNIT_NAME = "Audit Unit name";
    public static final String COLUMN_TEAM_MANAGER = "Team Manager";
    public static final String COLUMN_TEAM_MANAGER_GUID = "Team Manager GUID";
    public static final String COLUMN_TEAM_MANAGER_EMAIL = "Team Manager email";
    public static final String COLUMN_ENGAGEMENT_LEADER = "Engagement Leader";
    public static final String COLUMN_ENGAGEMENT_LEADER_GUID = "Engagement Leader GUID";
    public static final String COLUMN_ENGAGEMENT_LEADER_EMAIL = "Engagement Leader email";
    public static final String COLUMN_TEAM_MEMBER = "Team Member";
    public static final String COLUMN_TEAM_MEMBER_GUID = "Team Member GUID";
    public static final String COLUMN_TEAM_MEMBER_EMAIL = "Team Member email";
    private final SQLContext sqlContext;
    private final ResourceResolver resourceResolver;

    @Override
    public Long countColumns(String fileName) {
        return (long) getDataFrame(fileName).columns().length;
    }

    @Override
    public Long countRows(String fileName) {
        return getDataFrame(fileName).count();
    }

    @Override
    public List<Engagement> collectAuraData() {
        return getAuraRDD().collect();
    }

    @Override
    public JavaRDD<Engagement> getAuraRDD() {

        final Dataset<Row> engagements = fetchRequired("aura.csv");

        final Dataset<Row> byEngagementLeaders = engagements
                .groupBy(asScalaBuffer(singletonList(column(GUID))).seq())
                .agg(collect_set(AUDIT_UNIT).as(AUDIT_UNITS));

        final Dataset<Row> combidenDF = engagements
                .drop(AUDIT_UNIT)
                .join(byEngagementLeaders, GUID);

        return combidenDF.distinct().javaRDD().map(this::composeEngagement);
    }

    private Engagement composeEngagement(Row row) {
        return new Engagement(
                row.getAs(GUID),
                row.getAs(NAME),
                mapUsers(row.getAs(LEADERS_GUI_DS_RAW), row.getAs(LEADERS_EMAILS_RAW), row.getAs(LEADERS_FULL_NAMES_RAW)),
                mapUsers(row.getAs(MANAGERS_GUI_DS_RAW), row.getAs(MANAGERS_EMAILS_RAW), row.getAs(MANAGERS_FULL_NAMES_RAW)),
                mapUsers(row.getAs(MEMBERS_GUIDS_RAW), row.getAs(MEMBERS_EMAILS_RAW), row.getAs(MEMBERS_FULL_NAMES_RAW)),
                mapAuditUnits(row.getAs(AUDIT_UNITS))
        );
    }

    private Dataset<Row> fetchRequired(final String fileName) {

        final Seq<Column> leadersColumns = asScalaBuffer(asList(
                column(COLUMN_ENGAGEMENT_ID).as(GUID),
                column(COLUMN_ENGAGEMENT_NAME).as(NAME),

                column(COLUMN_AUDIT_UNIT_NAME).as(AUDIT_UNIT),

                column(COLUMN_TEAM_MANAGER).as(MANAGERS_FULL_NAMES_RAW),
                column(COLUMN_TEAM_MANAGER_GUID).as(MANAGERS_GUI_DS_RAW),
                column(COLUMN_TEAM_MANAGER_EMAIL).as(MANAGERS_EMAILS_RAW),

                column(COLUMN_ENGAGEMENT_LEADER).as(LEADERS_FULL_NAMES_RAW),
                column(COLUMN_ENGAGEMENT_LEADER_GUID).as(LEADERS_GUI_DS_RAW),
                column(COLUMN_ENGAGEMENT_LEADER_EMAIL).as(LEADERS_EMAILS_RAW),

                column(COLUMN_TEAM_MEMBER).as(MEMBERS_FULL_NAMES_RAW),
                column(COLUMN_TEAM_MEMBER_GUID).as(MEMBERS_GUIDS_RAW),
                column(COLUMN_TEAM_MEMBER_EMAIL).as(MEMBERS_EMAILS_RAW)
        )).seq();

        return getDataFrame(fileName).select(leadersColumns);
    }

    private List<User> mapUsers(String rawGUIDs, String rawEmails, String rawFullNames) {
        final List<String> GUIDs = asList(rawGUIDs.split(","));
        final List<String> emails = asList(rawEmails.split(","));
        final List<String> fullName = asList(rawFullNames.split(","));

        return IntStream.range(0, GUIDs.size())
                .mapToObj(i -> new User(GUIDs.get(i).trim(), emails.get(i).trim(), fullName.get(i).trim()))
                .collect(Collectors.toList());
    }

    private List<String> mapAuditUnits(Seq<String> auditUnits) {
        final List<String> as = JavaConversions.seqAsJavaList(auditUnits);
        return as.stream().map(String::trim).collect(Collectors.toList());
    }

    private Dataset<Row> getDataFrame(String fileName) {
        return sqlContext.read()
                .format("csv")
                .option("header", true)
                .option("inferSchema", true)
                .load(resourceResolver.resolve(fileName));
    }
}
