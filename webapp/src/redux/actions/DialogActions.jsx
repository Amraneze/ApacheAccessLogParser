import { DialogConstants } from '../constants/';

const closeDialog = (dispatch, callback) => {
    dispatch({
        type: DialogConstants.CHANGE_DIALOG_VISIBILITY,
        show: false,
    });
    callback && callback();
}

export function displayDialog(dispatch, callback) {
    dispatch({
        type: DialogConstants.CHANGE_DIALOG_VISIBILITY,
        show: true,
        props: {
            fullScreen: false,
            open: true,
            handleClose: () => closeDialog(dispatch, callback),
            dialog: {
              title: 'Service indisponible',
              text: 'Le service est momentanément indisponible. Veuillez nous excuser pour la gêne occasionnée. Nous vous invitons à réessayer ultérieurement.',
              buttons: {
                primary: {
                  text: 'Ok',
                  handler: () => closeDialog(dispatch, callback),
                },
              }
            }
        }
    });
}

