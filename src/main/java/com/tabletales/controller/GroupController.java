package com.tabletales.controller;

import com.tabletales.entity.Group;
import com.tabletales.repository.GroupRepository;
import com.tabletales.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/groups")
@RequiredArgsConstructor
public class GroupController {

    private final GroupRepository groupRepository;
    private final UserRepository userRepository;

    @GetMapping
    public List<Group> findGroups() {
        return groupRepository.findAll();
    }

    @PostMapping
    public Group createGroup(@RequestBody @Valid Group group) {
        return groupRepository.save(group);
    }

    @PutMapping("/{groupId}")
    public Group updateGroup(
        @PathVariable UUID groupId,
        @RequestBody @Valid Group group
    ) {
        var oldGroup = groupRepository.findById(groupId).orElseThrow();
        oldGroup.setName(group.getName());
        return groupRepository.save(oldGroup);
    }

    @DeleteMapping("/{groupId}")
    public void deleteGroup(@PathVariable UUID groupId) {
        groupRepository.deleteById(groupId);
    }

    @PutMapping("/join/{groupId}")
    public void joinGroup(
        @PathVariable UUID groupId,
        @RequestParam UUID userId
    ) {
        var group = groupRepository.findById(groupId).orElseThrow();
        var user = userRepository.findById(userId).orElseThrow();
        group.getUsers().add(user);
        groupRepository.save(group);
    }

    @PutMapping("/exit/{groupId}")
    public void exitGroup(
        @PathVariable UUID groupId,
        @RequestParam UUID userId
    ) {
        var group = groupRepository.findById(groupId).orElseThrow();
        var newGroupUsers = group.getUsers()
            .stream()
            .filter(user -> !user.getId().equals(userId))
            .toList();
        group.setUsers(newGroupUsers);
        groupRepository.save(group);
    }

}
