package com.paul.spark.dataframe;

import com.paul.spark.model.Engagement;
import com.paul.spark.model.User;
import lombok.RequiredArgsConstructor;
import org.apache.spark.sql.Column;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SQLContext;
import org.springframework.stereotype.Service;
import scala.collection.mutable.Seq;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.apache.spark.sql.functions.collect_set;
import static org.apache.spark.sql.functions.column;
import static scala.collection.JavaConversions.asScalaBuffer;
import static scala.collection.JavaConversions.seqAsJavaList;


@Service
@RequiredArgsConstructor
public class DataFrameExampleImpl implements DataFrameExample {

    private final SQLContext sqlContext;

    @Override
    public Long countColumns(String fileName) {
        return (long) getDataFrame(fileName).columns().length;
    }

    @Override
    public Long countRows(String fileName) {
        return getDataFrame(fileName).count();
    }

    @Override
    public Long countRowsDistinct(String fileName) {
        return fetchRequired(fileName).select("guid").distinct().count();
    }

    @Override
    public List<Engagement> collectData(String fileName) {

        final Dataset<Row> engagements = fetchRequired(fileName);

        final Dataset<Row> byEngagementLeaders = engagements
                .groupBy(asScalaBuffer(singletonList(column("guid"))).seq())
                .agg(collect_set("auditUnit").as("auditUnits"));

        final Dataset<Row> combidenDF = engagements
                .drop("auditUnit")
                .join(byEngagementLeaders, "guid");

        return combidenDF.distinct().javaRDD().map(this::composeEngagement).collect();
    }

    private Engagement composeEngagement(Row row) {
        return new Engagement(
                row.getAs("guid"),
                row.getAs("name"),
                mapUsers(row.getAs("leadersGUIDsRaw"), row.getAs("leadersEmailsRaw"), row.getAs("leadersFullNamesRaw")),
                mapUsers(row.getAs("managersGUIDsRaw"), row.getAs("managersEmailsRaw"), row.getAs("managersFullNamesRaw")),
                mapUsers(row.getAs("membersGUIDsRaw"), row.getAs("membersEmailsRaw"), row.getAs("membersFullNamesRaw")),
                mapAuditUnits(row.getAs("auditUnits"))
        );
    }

    private Dataset<Row> fetchRequired(final String fileName) {

        final Seq<Column> leadersColumns = asScalaBuffer(asList(
                column("EngagementID").as("guid"),
                column("Engagement Name").as("name"),

                column("Audit Unit name").as("auditUnit"),

                column("Team Manager").as("managersFullNamesRaw"),
                column("Team Manager GUID").as("managersGUIDsRaw"),
                column("Team Manager email").as("managersEmailsRaw"),

                column("Engagement Leader").as("leadersFullNamesRaw"),
                column("Engagement Leader GUID").as("leadersGUIDsRaw"),
                column("Engagement Leader email").as("leadersEmailsRaw"),

                column("Team Member").as("membersFullNamesRaw"),
                column("Team Member GUID").as("membersGUIDsRaw"),
                column("Team Member email").as("membersEmailsRaw")
        )).seq();

        return getDataFrame(fileName).select(leadersColumns);
    }

    private Set<User> mapUsers(String rawGUIDs, String rawEmails, String rawFullNames) {
        final List<String> GUIDs = asList(rawGUIDs.split(","));
        final List<String> emails = asList(rawEmails.split(","));
        final List<String> fullName = asList(rawFullNames.split(","));

        return IntStream.range(0, GUIDs.size())
                .mapToObj(i -> new User(GUIDs.get(i).trim(), emails.get(i).trim(), fullName.get(i).trim()))
                .collect(Collectors.toSet());
    }

    private Set<String> mapAuditUnits(Seq<String> auditUnits) {
        return seqAsJavaList(auditUnits).stream().map(String::trim).collect(Collectors.toSet());
    }

    private Dataset<Row> getDataFrame(String fileName) {
        return sqlContext.read()
                .format("csv")
                .option("header", true)
                .option("inferSchema", true)
                .load("/home/test/datasets/" + fileName);
    }
}
