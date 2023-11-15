import { createRouter, createWebHistory } from "vue-router";
import TheMainView from "@/views/TheMainView.vue";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/",
      name: "main",
      component: TheMainView,
    },
    {
      path: "/qna",
      name: "qna",
      component: () => import("@/views/TheQnaView.vue"),
      redirect: { name: "qna-board-list" },
      children: [
        {
          path: "list",
          name: "qna-board-list",
          component: () => import("@/components/qna/QnaBoardList.vue"),
        },
        {
          path: ":article_no",
          name: "qna-board-view",
          component: () => import("@/components/qna/QnaBoardDetail.vue"),
        },
        {
          path: "write",
          name: "qna-board-write",
          component: () => import("@/components/qna/QnaBoardWrite.vue"),
        },
        {
          path: "modify/:article_no",
          name: "qna-board-modify",
          component: () => import("@/components/qna/QnaBoardModify.vue"),
        },
      ],
    },
    {
      path: "/board",
      name: "board",
      component: () => import("@/views/TheBoardView.vue"),
      redirect: { name: "board-list" },
      children: [
        {
          path: "list",
          name: "board-list",
          component: () => import("@/components/board/BoardList.vue"),
        },
        {
          path: ":article_no",
          name: "board-view",
          component: () => import("@/components/board/BoardDetail.vue"),
        },
        {
          path: "write",
          name: "board-write",
          component: () => import("@/components/board/BoardWrite.vue"),
        },
        {
          path: "modify/:article_no",
          name: "board-modify",
          component: () => import("@/components/board/BoardModify.vue"),
        },
      ],
    },
  ],
});

export default router;
