package org.example.trees.commons;

import java.util.Arrays;
import java.util.List;

public class GenerateIntTree {
    public static Tree generate(){
        TreeNode firstNode = new TreeNode(0);
        TreeNode secondNode = new TreeNode(1);
        TreeNode thirdNode = new TreeNode(2);
        TreeNode fourthNode = new TreeNode(3);
        TreeNode fifthNode = new TreeNode(4);
        TreeNode sixthNode = new TreeNode(5);
        TreeNode seventhNode = new TreeNode(6);
        TreeNode eighthNode = new TreeNode(7);
        TreeNode ninthNode = new TreeNode(8);
        TreeNode tenthNode = new TreeNode(9);
        TreeNode eleventhNode = new TreeNode(10);
        TreeNode twelfthNode = new TreeNode(11);
        TreeNode thirteenthNode = new TreeNode(12);
        TreeNode fourthteenthNode = new TreeNode(13);

        firstNode.setChildren(new TreeNode[] { secondNode, thirdNode, fourthNode });
        secondNode.setChildren(new TreeNode[] { fifthNode, sixthNode });
        secondNode.setParent(firstNode);
        thirdNode.setChildren(new TreeNode[] { seventhNode, eighthNode });
        thirdNode.setParent(firstNode);
        fourthNode.setChildren(new TreeNode[] { ninthNode, tenthNode });
        fourthNode.setParent(firstNode);
        fifthNode.setChildren(new TreeNode[] { eleventhNode });
        fifthNode.setParent(secondNode);
        sixthNode.setChildren(new TreeNode[] { twelfthNode, thirteenthNode });
        sixthNode.setParent(secondNode);
        eighthNode.setChildren(new TreeNode[] { fourthteenthNode });
        eighthNode.setParent(thirdNode);

        ninthNode.setParent(fourthNode);
        tenthNode.setParent(fourthNode);
        eleventhNode.setParent(fifthNode);
        twelfthNode.setParent(sixthNode);
        thirteenthNode.setParent(sixthNode);
        fourthteenthNode.setParent(eighthNode);

        return new Tree(firstNode);
    }
}
