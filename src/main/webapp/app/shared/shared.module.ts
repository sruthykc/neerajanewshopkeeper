import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { NgbDateAdapter } from '@ng-bootstrap/ng-bootstrap';

import { NgbDateMomentAdapter } from './util/datepicker-adapter';
import { ShopkeepergatewaySharedLibsModule, ShopkeepergatewaySharedCommonModule, HasAnyAuthorityDirective } from './';

@NgModule({
    imports: [ShopkeepergatewaySharedLibsModule, ShopkeepergatewaySharedCommonModule],
    declarations: [HasAnyAuthorityDirective],
    providers: [{ provide: NgbDateAdapter, useClass: NgbDateMomentAdapter }],
    exports: [ShopkeepergatewaySharedCommonModule, HasAnyAuthorityDirective],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ShopkeepergatewaySharedModule {
    static forRoot() {
        return {
            ngModule: ShopkeepergatewaySharedModule
        };
    }
}
