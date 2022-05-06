package structs;

import java.util.List;

public record MainCategoryEntryRecord(List<SubCategoryEntryRecord> subCategories, String name) {
}
