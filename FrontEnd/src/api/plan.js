import { localAxios } from "@/util/http-commons";

const local = localAxios();

const url = "/plan";

function listPlan(param, success, fail) {
  local.get(`${url}`, { params: param }).then(success).catch(fail);
}

function detailPlan(plan_no, success, fail) {
  local.get(`${url}/${plan_no}`).then(success).catch(fail);
}

function registerPlan(plan, success, fail) {
  local.post(`${url}`, JSON.stringify(plan)).then(success).catch(fail);
}

export { listPlan, detailPlan, registerPlan };
