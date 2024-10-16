import {HOST} from '../../commons/hosts';
import RestApiClient from "../../commons/api/rest-client";
import { resetWarningCache } from 'prop-types/checkPropTypes';

const endpoint = {
    manage: '/manage',
};



function getValuesMeasured(id, callback)
{
    let request = new Request(HOST.manage_device + endpoint.manage+"/findAll/"+id, {
        method: 'GET',
    });
    console.log(request.url);
    RestApiClient.performRequest(request, callback);
}


export {
    getValuesMeasured
};
