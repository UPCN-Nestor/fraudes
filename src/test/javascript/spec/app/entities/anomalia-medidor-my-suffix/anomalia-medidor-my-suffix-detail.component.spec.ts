/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { FrTestModule } from '../../../test.module';
import { AnomaliaMedidorMySuffixDetailComponent } from '../../../../../../main/webapp/app/entities/anomalia-medidor-my-suffix/anomalia-medidor-my-suffix-detail.component';
import { AnomaliaMedidorMySuffixService } from '../../../../../../main/webapp/app/entities/anomalia-medidor-my-suffix/anomalia-medidor-my-suffix.service';
import { AnomaliaMedidorMySuffix } from '../../../../../../main/webapp/app/entities/anomalia-medidor-my-suffix/anomalia-medidor-my-suffix.model';

describe('Component Tests', () => {

    describe('AnomaliaMedidorMySuffix Management Detail Component', () => {
        let comp: AnomaliaMedidorMySuffixDetailComponent;
        let fixture: ComponentFixture<AnomaliaMedidorMySuffixDetailComponent>;
        let service: AnomaliaMedidorMySuffixService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FrTestModule],
                declarations: [AnomaliaMedidorMySuffixDetailComponent],
                providers: [
                    AnomaliaMedidorMySuffixService
                ]
            })
            .overrideTemplate(AnomaliaMedidorMySuffixDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AnomaliaMedidorMySuffixDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AnomaliaMedidorMySuffixService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new AnomaliaMedidorMySuffix(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.anomaliaMedidor).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
