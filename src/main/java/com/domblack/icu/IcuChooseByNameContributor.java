package com.domblack.icu;

import com.intellij.navigation.ChooseByNameContributor;
import com.intellij.navigation.NavigationItem;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class IcuChooseByNameContributor implements ChooseByNameContributor {
    @NotNull
    @Override
    public String[] getNames(Project project, boolean includeNonProjectItems) {
        List<IcuMessage> properties = IcuUtil.findMessages(project);
        List<String> names = new ArrayList<>(properties.size());

        for (IcuMessage property : properties) {
            if (property.getName() != null && property.getName().length() > 0) {
                names.add(property.getName());
            }
        }

        return names.toArray(new String[0]);
    }

    @NotNull
    @Override
    public NavigationItem[] getItemsByName(String name, String pattern, Project project, boolean includeNonProjectItems) {
        List<IcuMessage> properties = IcuUtil.findMessages(project, name);

        return properties.toArray(new NavigationItem[0]);
    }
}
